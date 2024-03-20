package com.themoah.themoah.common.util.niceid;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.themoah.themoah.common.util.niceid.dto.NiceIdCryptoKey;
import com.themoah.themoah.common.util.niceid.dto.NiceIdCryptoToken;
import com.themoah.themoah.common.util.niceid.dto.NiceIdEncData;
import com.themoah.themoah.common.util.niceid.dto.NiceIdResultData;
import com.themoah.themoah.common.util.niceid.dto.enums.NiceIdStatusCode;
import com.themoah.themoah.common.util.niceid.dto.response.NiceIdAccessTokenResponse;
import com.themoah.themoah.common.util.niceid.dto.response.NiceIdCryptoTokenResponse;
import com.themoah.themoah.common.util.niceid.dto.response.NiceIdRevokeTokenResponse;
import com.themoah.themoah.common.util.niceid.exception.NiceIdValidationException;
import com.themoah.themoah.domain.verification.niceId.entity.NiceIdKey;
import com.themoah.themoah.domain.verification.niceId.repository.NiceIdKeyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
public class NiceIdVerification {
    private final static String BASIC_PREFIX = "Basic ";
    public final static String CLIENT_ID = "50ae6567-1ca6-4f75-a400-c36f4a6e7736";
    public final static String CLIENT_SECRET = "f123b0399e9541417dc3515f8333dc67";
    // 요청 데이터 설정
    private final static String BASIC_POST_DATA_REQUEST_BODY = "grant_type=client_credentials&scope=default";
    private final static String NICEID_BASE_URL = "https://svc.niceapi.co.kr:22001";
    private final static String NICEID_ACCESSTOKEN_REQ_URL = "/digital/niceid/oauth/oauth/token";
    private final static String NICEID_REVOKE_ACCESS_TOKEN_REQ_URL = "/digital/niceid/oauth/oauth/token/revokeById";
    private final static String NICEID_CRYPTOTOKEN_REQ_URL = "/digital/niceid/api/v1.0/common/crypto/token";
    private final static String HEADER_AUTHORIZATION = "Authorization";
    private final static String HEADER_PRODUCT_ID = "ProductID";
    public final static String PRODUCT_ID = "2101979031";
    public final static String DATE_FORMAT_yyyyMMddHHmmss = "yyyyMMddHHmmss";
    public final static String DATE_FORMAT_yyyyMMdd = "yyyyMMdd";
    public final static String AUTHORIZATION_PREFIX_BEARER = "bearer ";


    /**
     * 0. 실행 메서드
     */

    public static NiceIdEncData getNiceIdEncData(String clientId, String clientSecret, String productId, String returnURL, NiceIdKeyRepository niceIdKeyRepository) {

        // 1.AccessToken를 받아온다.
        String accessToken = getNiceIdAccessToken(clientId, clientSecret);

        // 2.AccessToken를 매개변수로, CryptoToken 가져온다.
        NiceIdCryptoToken niceIdCryptoToken = getNiceIdCryptoToken(accessToken, clientId, productId);

        //2-1. cryptoken를  repository에 저장한다.
        niceIdKeyRepository.save(NiceIdKey.builder()
                        .key(niceIdCryptoToken.getCryptoToken())
                        .tokenVersionId(niceIdCryptoToken.getTokenVersionId())
                .build());

        // 3.CryptoToken에서 key, iv, hmac_key를 추출한다.
        NiceIdCryptoKey niceIdCryptoKey = extractKeysFromCryptoToken(niceIdCryptoToken);

        // 4.추출된 keys 정보들을 가지고 있는 niceIdCryptoKey 가지고, 암호화된 요청 데이터과 이 암호화된 요청 데이터에 대한 무결성값을 생성한다.
        // + 반환
        return generateEncryptedRequestData(niceIdCryptoKey, returnURL);
    }

    /**
     * 1. CLIENT_ID와 CLIENT_SECRET으로 AccessToken를 생성합니다.
     *
     * @return String accessToken
     */

    private static String getNiceIdAccessToken(String clientId, String clientSecret) {

        String codes = clientId.trim() + ":" + clientSecret.trim();
        String base64Credentials = Base64.getEncoder().encodeToString(codes.getBytes());

        // 요청 URL 및 파라미터 설정
        String url = NICEID_BASE_URL + NICEID_ACCESSTOKEN_REQ_URL;
        URI uri = UriComponentsBuilder
                .fromUriString(url)
                .build()
                .toUri();

        // 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Basic " + base64Credentials);

        // 요청 바디 설정
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "client_credentials");
        body.add("scope", "default");

        // HTTP 요청 엔티티 생성
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

        // RestTemplate 객체 생성
        RestTemplate restTemplate = new RestTemplate();

        // HTTP POST 요청 보내기
        ResponseEntity<NiceIdAccessTokenResponse> responseEntity = restTemplate.exchange(uri, HttpMethod.POST, requestEntity, NiceIdAccessTokenResponse.class);

        // 응답 받기
        HttpStatusCode statusCode = responseEntity.getStatusCode();
        NiceIdAccessTokenResponse responseBody = responseEntity.getBody();

        //AccessToken
        String accessToken = responseBody.getDataBody().getAccessToken();

        if (statusCode == HttpStatus.OK) {
            return accessToken;
        } else {
            throw new RuntimeException("POST request failed with status code: " + statusCode + ", response: " + responseBody);
        }
    }

    /**
     * ACCESSTOKEN를 파기하는 메서드
     *
     * @param accessToken
     * @return
     */
    public static boolean revokeAccessToken(String accessToken, String clientId) {

        // 요청 URL 및 파라미터 설정
        String url = NICEID_BASE_URL + NICEID_REVOKE_ACCESS_TOKEN_REQ_URL;
        URI uri = UriComponentsBuilder
                .fromUriString(url)
                .build()
                .toUri();

        // 현재 타임스탬프 생성
        Date currentDate = new Date();
        long currentTimestamp = currentDate.getTime() / 1000;

        // access_token과 client_id 설정
        // parameter값으로 대체한다.

        // credentials 조합 및 Base64 인코딩
        String credentials = accessToken + ":" + currentTimestamp + ":" + clientId;
        String base64Credentials = Base64.getEncoder().encodeToString(credentials.getBytes());

        String authorizationHeader = BASIC_PREFIX + base64Credentials;

        // RestTemplate 객체 생성
        RestTemplate restTemplate = new RestTemplate();

        // 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", authorizationHeader);

        // 요청 데이터 설정 (없음)

        // HTTP POST 요청 보내기 및 응답 받아오기
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<NiceIdRevokeTokenResponse> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.POST,
                request,
                NiceIdRevokeTokenResponse.class
        );

        // 응답 출력
//        System.out.println("Response: " + responseEntity.getBody());
        return "1200".equals(responseEntity.getBody().getDataHeader().getResultCode());
    }

    /**
     * 2. ACCESSTOKEN을 매개변수로, 암호화된 rawkey인 cryptotoken과  sitecode를 담은 NiceIdCryptoToken객체로 반환합니다.
     *
     * @param accessToken
     */
    private static NiceIdCryptoToken getNiceIdCryptoToken(String accessToken, String clientId, String productId) {

        // 요청 URL 및 파라미터 설정
        String url = NICEID_BASE_URL + NICEID_CRYPTOTOKEN_REQ_URL;
        URI uri = UriComponentsBuilder
                .fromUriString(url)
                .build()
                .toUri();

        // 현재 타임스탬프 생성
        Date currentDate = new Date();
        long currentTimestamp = currentDate.getTime() / 1000;

        // 현재 시간을 YYYYMMDDHH24MISS 형식으로 변환
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_yyyyMMddHHmmss);
        String yyyyMMddHHmmss = sdf.format(currentDate);

        // credentials 조합 및 Base64 인코딩
        String credentials = accessToken.trim() + ":" + currentTimestamp + ":" + clientId.trim();
        String base64Credentials = Base64.getEncoder().encodeToString(credentials.getBytes());

        // "bearer " + Base64 인코딩된 credentials
        String authorizationHeader = AUTHORIZATION_PREFIX_BEARER + base64Credentials;

        // RestTemplate 객체 생성
        RestTemplate restTemplate = new RestTemplate();

        // 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HEADER_AUTHORIZATION, authorizationHeader);
        headers.set(HEADER_PRODUCT_ID, productId);

        // 요청 본문 생성(request body)
        JSONObject dataHeader = new JSONObject();
        dataHeader.put("CNTY_CD", "ko");

        String req_dtim = yyyyMMddHHmmss; // 요청일자
        String req_no = generateRandomNumber(); // 요청 고유번호

        JSONObject dataBody = new JSONObject();
        dataBody.put("req_dtim", req_dtim);
        dataBody.put("req_no", req_no);
        dataBody.put("enc_mode", "1");

        JSONObject requestBody = new JSONObject();
        requestBody.put("dataHeader", dataHeader);
        requestBody.put("dataBody", dataBody);

        // HTTP POST 요청 보내기 및 응답 받아오기
        HttpEntity<String> request = new HttpEntity<>(requestBody.toString(), headers);
        ResponseEntity<NiceIdCryptoTokenResponse> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.POST,
                request,
                NiceIdCryptoTokenResponse.class
        );

        String cryptoToken = generateCryptoToken(req_dtim, req_no, responseEntity.getBody().getDataBody().getTokenVal());
        String siteCode = responseEntity.getBody().getDataBody().getSiteCode();
        String tokenVersionId = responseEntity.getBody().getDataBody().getTokenVersionId();

        return NiceIdCryptoToken.builder()
                .cryptoToken(cryptoToken)
                .siteCode(siteCode)
                .tokenVersionId(tokenVersionId)
                .build();
    }

    /**
     * 2-1. 암호화 토큰의 요청 파리미터, 응답 파라미터를 가지고, CryptoToken를 생성합니다.
     * - CryptoToken --> key, iv, hmac_key를 포함하는 token
     *
     * @param req_dtim  // YYYYMMDDHH24MISS
     * @param req_no    // 요청고유번호
     * @param token_val // 암복호화를 위한 서버 토큰 값
     * @return String cryptoToken
     */
    private static String generateCryptoToken(String req_dtim, String req_no, String token_val) {
        String value = req_dtim.trim() + req_no.trim() + token_val.trim();

        try {
            // SHA-256 해시 함수 사용
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(value.getBytes());

            byte[] arrHashValue = md.digest();

            // Base64 인코딩 및 반환
            return Base64.getEncoder().encodeToString(arrHashValue);

        } catch (NoSuchAlgorithmException e) {
            log.error("NoSuchAlgorithmException occur!");
            throw new RuntimeException("NoSuchAlgorithmException occur!");
        }
    }

    /**
     * 2-2. 요청 고유번호 랜덤 값 생성(long random value --> String)
     *
     * @return String
     */
    private static String generateRandomNumber() {
        // Random 객체 생성
        Random random = new Random();

        // 30자리 이하의 난수 생성
        long randomNumber = (long) (random.nextDouble() * Math.pow(10, 28));

        // 결과 출력
        return String.valueOf(randomNumber);
    }


    /**
     * 3 NiceIdCryptoToken을 가지고 대칭키, 초기화벡터, 사이트코드를 정보를 가진 NiceIdCryptoKey객체로 바꿉니다.
     *
     * @return
     */
    private static NiceIdCryptoKey extractKeysFromCryptoToken(NiceIdCryptoToken cryptoToken) {

        String key = extractKeyFromCryptoToken(cryptoToken.getCryptoToken());
        String iv = extractIvFromCryptoToken(cryptoToken.getCryptoToken());
        String hmackey = extractHmacKeyFromCryptoToken(cryptoToken.getCryptoToken());
        String sitecode = cryptoToken.getSiteCode();

        return NiceIdCryptoKey.builder()
                .key(key)
                .iv(iv)
                .hmacKey(hmackey)
                .siteCode(sitecode)
                .tokenVersionId(cryptoToken.getTokenVersionId())
                .build();
    }

    /**
     * 3-2. RawKey에서 데이터암호화할 대칭키(SymmetricKey) 추출
     *
     * @param cryptoToken
     * @return SymmetricKey
     */
    private static String extractKeyFromCryptoToken(String cryptoToken) {
        // 앞에서부터 16바이트를 key값으로 사용
        return cryptoToken.substring(0, 16);
    }

    /**
     * 3-3. RawKey에서 데이터암호화할 InitailVector(초기화벡터) 추출
     *
     * @param cryptoToken
     * @return InitailVector
     */
    private static String extractIvFromCryptoToken(String cryptoToken) {
        // 뒤에서부터 16바이트를 IV 값으로 사용
        int length = cryptoToken.length();
        return cryptoToken.substring(length - 16);
    }

    /**
     * 3-4. RawKey에서 암호화값 위변조 체크용 hmac_key 추출
     *
     * @param cryptoToken
     * @return String
     */
    private static String extractHmacKeyFromCryptoToken(String cryptoToken) {
        // 앞에서부터 32바이트를 HMAC 키 값으로 사용
        return cryptoToken.substring(0, 32);
    }

    /**
     * 4. 요청데이터 암호화
     * - NiceIdCryptoKey를 매개변수로 암호화한 요청정보와 암호호화한 요청정보의 무결성 값을 반환합니다.
     * - NiceIdEncryptedRequestData(tokenVersionId(서버 토큰 버전), encData(암호화 요청데이터), integrityValue(Hmac 무결성체크값)) 반환
     * 4-1. JSON형태의 요청 데이터 암호화
     * 4-2. Hmac 무결성 체크값(integrity_value) 생성
     * 4-3. NiceIdEncryptedRequestData에 정보를 담아서 반환
     */

    private static NiceIdEncData generateEncryptedRequestData(NiceIdCryptoKey cryptoKey, String returnUrl) {

        try {
            // Map 객체 생성
            Map<String, String> requestDataMap = new HashMap<>();
            requestDataMap.put("requestno", generateRequestNo());
            requestDataMap.put("returnurl", returnUrl);
            requestDataMap.put("sitecode", cryptoKey.getSiteCode());
            requestDataMap.put("methodtype", "get");
            requestDataMap.put("popupyn", "Y");
            requestDataMap.put("receivedata", "Personal authentication data success");

            // ObjectMapper를 사용하여 맵을 JSON 문자열로 변환
            ObjectMapper objectMapper = new ObjectMapper();

            String reqData = objectMapper.writeValueAsString(requestDataMap);

            // 암호화 키 및 IV 생성
            SecretKey secureKey = new SecretKeySpec(cryptoKey.getKey().getBytes(), "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(cryptoKey.getIv().getBytes());

            // 암호화를 위한 Cipher 객체 생성 및 초기화
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secureKey, ivParameterSpec);

            // 요청 데이터를 바이트 배열로 변환하고 암호화 수행
            byte[] encrypted = cipher.doFinal(reqData.trim().getBytes());

            // 암호화된 데이터를 Base64로 인코딩하여 출력
            String enc_data = Base64.getEncoder().encodeToString(encrypted);

            byte[] hmacSha256 = hmac256(cryptoKey.getHmacKey().getBytes(), enc_data.getBytes());

            String integrity_value = Base64.getEncoder().encodeToString(hmacSha256);

            return NiceIdEncData.builder()
                    .tokenVersionId(cryptoKey.getTokenVersionId())
                    .encData(enc_data)
                    .integrityValue(integrity_value)
                    .build();

        } catch (InvalidAlgorithmParameterException e) {
            throw new RuntimeException("데이터 암호화 예외 발생(1)");
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException("데이터 암호화 예외 발생(2)");
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException("데이터 암호화 예외 발생(3)");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("데이터 암호화 예외 발생(4)");
        } catch (BadPaddingException e) {
            throw new RuntimeException("데이터 암호화 예외 발생(5)");
        } catch (InvalidKeyException e) {
            throw new RuntimeException("데이터 암호화 예외 발생(6)");
        } catch (JsonProcessingException e) {
            throw new RuntimeException("데이터 암호화 예외 발생(7)");
        }
    }

    /**
     * 4-1. 요청 번호 만들기
     */
    private static String generateRequestNo() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT_yyyyMMdd);
        String currentDate = simpleDateFormat.format(new Date());
        int authNo = (int)(Math.random() * (99999 - 10000 + 1)) + 10000;
        return "REQ" + currentDate + authNo;
    }

    /**
     * 4-2. Hmac 무결성 체크값(integrity_value) 생성
     */
    private static byte[] hmac256(byte[] secretKey, byte[] message) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec sks = new SecretKeySpec(secretKey, "HmacSHA256");
            mac.init(sks);
            return mac.doFinal(message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate HMACSHA256 encrypt", e);
        }
    }

    /**
     * 5. 데이터 복호화 메서드
     */
    public static NiceIdResultData getDecryptedEncData(String cryptoToken, String encData) {
        NiceIdResultData niceIdResultData = decryptResponseData(cryptoToken, encData);
        boolean rst = validateNiceIdResultData(niceIdResultData);
        if(rst) {
            return niceIdResultData;
        } else {
            return null;
        }
    }

    private static NiceIdResultData decryptResponseData(String cryptoToken, String encData) {
        try {
            // 암호화에 사용된 키와 IV
            String key = extractKeyFromCryptoToken(cryptoToken);
            String iv = extractIvFromCryptoToken(cryptoToken);

            // 암호화된 데이터를 Base64 디코딩
            byte[] cipherEnc = Base64.getDecoder().decode(encData);

            // 키 생성
            SecretKey secureKey = new SecretKeySpec(key.getBytes(), "AES");

            // 복호화를 위한 Cipher 객체 생성
            Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
            c.init(Cipher.DECRYPT_MODE, secureKey, new IvParameterSpec(iv.getBytes()));

            // 복호화 수행
            byte[] decryptedBytes = c.doFinal(cipherEnc);

            // 복호화된 데이터 문자열로 변환
            String decryptedData = new String(decryptedBytes, "euc-kr");

            //복호화된 JSON data를 클래스(Class)로 변환
            ObjectMapper objectMapper = new ObjectMapper();
            NiceIdResultData niceIdResultData = objectMapper.readValue(decryptedData, NiceIdResultData.class);

            // 복호화된 데이터 반환
            return niceIdResultData;

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("데이터 복호화 예외 발생(1)");
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException("데이터 복호화 예외 발생(2)");
        } catch (InvalidKeyException e) {
            throw new RuntimeException("데이터 복호화 예외 발생(3)");
        } catch (InvalidAlgorithmParameterException e) {
            throw new RuntimeException("데이터 복호화 예외 발생(4)");
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException("데이터 복호화 예외 발생(5)");
        } catch (BadPaddingException e) {
            throw new RuntimeException("데이터 복호화 예외 발생(6)");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("데이터 복호화 예외 발생(7)");
        } catch (JsonMappingException e) {
            throw new RuntimeException("paring JSON to convert class fails.(1)");
        } catch (JsonProcessingException e) {
            throw new RuntimeException("paring JSON to convert class fails.(2)");
        }
    }

    /**
     * NiceId API에서 받은 복호화된 데이터를 JAVA CLASS화
     * @param niceIdResultData
     * @return
     */
    private static boolean validateNiceIdResultData(NiceIdResultData niceIdResultData) {
        String resultCode = niceIdResultData.getResultCode();

        NiceIdStatusCode rst = Arrays.stream(NiceIdStatusCode.values())
                .filter(niceIdStatusCode -> niceIdStatusCode.getCode().equals(niceIdResultData.getResultCode()))
                .findAny().orElse(NiceIdStatusCode.OTHER_ERRORS);
        
        if(rst == NiceIdStatusCode.AUTHENTICATION_SUCCESS) {
            return true;
        }

        throw new NiceIdValidationException(rst);
    }
}



