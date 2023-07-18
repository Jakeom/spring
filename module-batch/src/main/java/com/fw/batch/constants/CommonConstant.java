package com.fw.batch.constants;

/**
 * 공통 상수
 */
public class CommonConstant {
    /** SPRING PROFILE 유형 */
    public static class SPRING_PROFILE {
        /** LOCAL */
        public final static String LOCAL = "local";
        
        /** DEV */
        public final static String DEV = "dev";
        
        /** REAL */
        public final static String REAL = "real";
    }
    
    /** 세션키 */
    public static class SESSION_KEY {
        /** 관리자 */
        public final static String ADMIN = "SESSION_KEY_ADMIN";
    }
    
    /** AJAX */
    public static class AJAX {
        /** 정상 */
        public final static boolean OK = true;
        
        /** 에러 */
        public final static boolean NG = false;
        
        /** 결과 */
        public final static String RESULT = "result";
        
        /** 메시지 */
        public final static String MSG = "message";
        
        /** 코드 */
        public final static String CD = "code";
        
        /** 리스트 */
        public final static String RECORD = "record";
        
        /** 정보 */
        public final static String INFO = "info";
        
        /** HTTP 상태 */
        public final static String HTTP_STATUS = "status";
    }
    
    /** 데이터 테이블 */
    public static class DATA_TABLE {
        /** 데이터 리스트 */
        public final static String DATA_LIST = "dataList";
        
        /** 데이터 총 건수 */
        public final static String TOTAL_CNT = "records";
        
        /** 현재 페이지 번호 */
        public final static String PAGE_NO = "page";
        
        /** 페이지 총 건수 */
        public final static String TOTAL_PAGE_CNT = "total";
    }
    
    /** 엑셀 */
    public static class EXCEL {
        public final static String TEMPLATE_ID = "templateId";
        public final static String TEMPLATE_NAME = "templateName";
        public final static String ERRLIST = "errList";
    }
    
    /** 엑셀업로드 Validation Type */
    public enum ExcelValidType {
        EMAIL, PHONE
    }
    
    /** 오류 코드 */
    public static class MSG_CODE {
        /** 정상 처리되었습니다. */
        public final static String MSG_01 = "MSG_01";
        
        /** 로그인 하였습니다. */
        public final static String MSG_02 = "MSG_02";
        
        /** 로그인 실패하였습니다. */
        public final static String MSG_03 = "MSG_03";
        
        /** 권한이 없습니다. */
        public final static String MSG_04 = "MSG_04";
        
        /** 저장 되었습니다. */
        public final static String MSG_05 = "MSG_05";
        
        /** 성공 하였습니다. */
        public final static String MSG_06 = "MSG_06";
        
        /** 세션이 만료되었습니다. */
        public final static String MSG_07 = "MSG_07";
        
        /** 이메일 주소가 유효하지 않습니다. */
        public final static String MSG_08 = "MSG_08";
        
        /** 비밀번호가 유효하지 않습니다. */
        public final static String MSG_09 = "MSG_09";
        
        /** 로그인 미승인 상태입니다. */
        public final static String MSG_10 = "MSG_10";
        
        /** {0}가 중복 되었습니다. */
        public final static String MSG_11 = "MSG_11";
        
        /** {0} 성공하였습니다. */
        public final static String MSG_20 = "MSG_20";
        
        /** {0} 리다이렉트 합니다. */
        public final static String MSG_21 = "MSG_21";
        
        /** {0} 등록하지 않은 URI 입니다. */
        public final static String MSG_22 = "MSG_22";
        
        /** 잘못된 접근입니다. */
        public final static String MSG_23 = "MSG_23";
        
        /** 유효 기간이 만료 되었습니다. */
        public final static String MSG_24 = "MSG_24";
        
        /** {0}은 최대 {1}개까지 등록 가능합니다. */
        public final static String MSG_25 = "MSG_25";
        
        /** {0} 오류가 발생하였습니다.{1} */
        public final static String MSG_26 = "MSG_26";
        
        /** 태그의 길이는 {0}자를 초과할 수 없습니다. */
        public final static String MSG_27 = "MSG_27";
        
        /** 시스템에러가 발생했습니다. 시스템관리자에게 문의 하세요. */
        public final static String MSG_99 = "MSG_99";

//        /** 엑셀파일에 대한 항목 정의가 없습니다. */
//        public final static String EXCEL_MSG_0001 = "EXCEL_MSG_0001";
//        /** 숫자만 입력할수 있습니다. */
//        public final static String EXCEL_MSG_0002 = "EXCEL_MSG_0002";
//        /** 숫자({0}자리)만 입력할수 있습니다. */
//        public final static String EXCEL_MSG_0003 = "EXCEL_MSG_0003";
//        /** 숫자({0}~{1}자리)만 입력할수 있습니다. */
//        public final static String EXCEL_MSG_0004 = "EXCEL_MSG_0004";
//        /** 문자({0}자리)만 입력할수 있습니다. */
//        public final static String EXCEL_MSG_0005 = "EXCEL_MSG_0005";
//        /** 문자({0}~{1}자리)만 입력할수 있습니다. */
//        public final static String EXCEL_MSG_0006 = "EXCEL_MSG_0006";
//        /** 이메일 형식이 올바르지 않습니다. */
//        public final static String EXCEL_MSG_0007 = "EXCEL_MSG_0007";
//        /** 휴대폰번호 형식이 올바르지 않습니다. */
//        public final static String EXCEL_MSG_0008 = "EXCEL_MSG_0008";
//        /** 전화번호 형식이 올바르지 않습니다. */
//        public final static String EXCEL_MSG_0009 = "EXCEL_MSG_0009";
//        /** 우편번호 형식이 올바르지 않습니다. */
//        public final static String EXCEL_MSG_0010 = "EXCEL_MSG_0010";
//        /** 코드값을 확인해 주세요. */
//        public final static String EXCEL_MSG_0011 = "EXCEL_MSG_0011";
        
        
        /** {0}라인 : 필수 항목을 입력 해 주세요. */
        public final static String EXCEL_MSG_01 = "EXCEL_MSG_01";
        
        /** {0}라인 : 이메일을 확인 해 주세요. */
        public final static String EXCEL_MSG_02 = "EXCEL_MSG_02";
        
        /** {0}라인 : 휴대폰번호를 확인 해 주세요. */
        public final static String EXCEL_MSG_03 = "EXCEL_MSG_03";
        
        /**최대 입력 행을 초과 하였습니다.{0}행 까지 입력 가능합니다.*/
        public final static String EXCEL_MAX_ROW_ERR = "EXCEL_MAX_ROW_ERR";
    }
    
    /**  웹소켓 환경 */
    public static class WEBSOCKET {
        /** END POINT */
        public static final String END_POINT = "monitoring";
        
        /** 어플리케이션 접두어 */
        public static final String APP_PREFIX = "/app";
        
        /** TOPIC_BROKER */
        public static final String TOPIC_BROKER = "/topic";
        
        /** QUEUE_BROKER */
        public static final String QUEUE_BROKER = "/queue";
        
        /** USER_BROKER */
        public static final String USER_BROKER = "/user";
    }
    
    /** 배치구분 */
    public static class BATCH_STATUS {
        /** 대기 */
        public final static String WAIT = "0";
        
        /** 처리중 */
        public final static String ING = "1";
        
        /** 정상종료 */
        public final static String OK = "2";
        
        /** 이상종료 */
        public final static String NG = "3";
    }
    
    /** 배치 유형 */
    public static class BATCH_TYPE {
        /** 배치 로그 테이블 삭제 */
        public final static String BATCH_LOG = "BATCH_LOG";
        
        /** 매월 배치 */
        public final static String MONTH = "MONTH";
        
        /** 일일 배치 */
        public final static String DATE = "DATE";
        
        /** 일일 낮 시간 배치 */
        public final static String DAY = "DAY";
        
        /** 매시 배치 */
        public final static String HOUR = "HOUR";
        
        /** 30분 배치 */
        public final static String HALF = "HALF";
        
        /** 10분 배치 */
        public final static String MINUTE10 = "MINUTE10";
        
        /** 매분 배치 */
        public final static String MINUTE = "MINUTE";
    }
    
    /** 계정 유형 */
    public static class ACCOUNT_TYPE {
        /** 마스터 */
        public final static String MASTER = "MASTER";
        
        /** 서브 마스터 */
        public final static String SUB_MASTER = "SUB_MASTER";
        
        /** 슈퍼 루키 */
        public final static String SUPER_LUKIE = "SUPER_LUKIE";
        
        /** 루키 */
        public final static String LUKIE = "LUKIE";
    }
    
    /** 승인 유무 */
    public static class APPROVAL_YN {
        /** 승인 */
        public final static String Y = "Y";
        
        /** 미승인 */
        public final static String N = "N";
    }
    
    /** 최상위유무 */
    public static class IMP_YN {
        /** 비최상위 */
        public final static String N = "N";
        
        /** 상위 */
        public final static String Y = "Y";
    }
    
    /** 사용 유무 */
    public static class USE {
        /** 사용 */
        public final static String Y = "Y";
        
        /** 미사용 */
        public final static String N = "N";
    }
    
    /** 삭제 유무 */
    public static class DEL {
        /** 삭제 : Y */
        public final static String Y = "Y";
        
        /** 삭제 : N */
        public final static String N = "N";
    }
    
    /** 신규 유무 */
    public static class NEW {
        /** 신규 */
        public final static String Y = "Y";
        
        /** 기존 */
        public final static String N = "N";
    }
    
    /** 오류 유무 */
    public static class ERROR {
        /** 오류 */
        public final static String Y = "Y";
        
        /** 정상 */
        public final static String N = "N";
    }
    
    /** 회원 유무 */
    public static class JOIN {
        /** 가입 */
        public final static String Y = "Y";
        
        /** 미가입 */
        public final static String N = "N";
    }
    
    /** 답변 유무 */
    public static class ANSWER_YN {
        /** 답변 완료 */
        public final static String Y = "Y";
        
        /** 답변 대기 */
        public final static String N = "N";
    }
    
    /** 스케줄러 주기 */
    public static class CYCLE_TYPE {
        /** 분 */
        public final static String MINUTE = "MINUTE";
        
        /** 시간 */
        public final static String HOUR = "HOUR";
        
        /** 일 */
        public final static String DATE = "DATE";
    }
    
    /** 날짜 유형 */
    public static class DATE_TYPE {
        /** 전체 */
        public final static String ALL = "ALL";
        
        /** 연도 */
        public final static String YEAR = "YEAR";
        
        /** 월 */
        public final static String MONTH = "MONTH";
        
        /** 일 */
        public final static String DATE = "DATE";
        
        /** 주 */
        public final static String WEEK = "WEEK";
    }
    
    /** 요일 유형 */
    public static class DAY_WEEK_TYPE {
        /** 일요일 */
        public final static int SUN = 1;
        
        /** 월요일 */
        public final static int MON = 2;
        
        /** 화요일 */
        public final static int TUE = 3;
        
        /** 수요일 */
        public final static int WED = 4;
        
        /** 목요일 */
        public final static int THU = 5;
        
        /** 금요일 */
        public final static int FRI = 6;
        
        /** 토요일 */
        public final static int SAT = 7;
    }
    
    /** 관리자 유형 */
    public static class ADMIN_TYPE {
        /** 슈퍼 관리자 */
        public final static String SUPER = "SUPER";
        
        /** 관리자 */
        public final static String ADMIN = "ADMIN";
    }
    
    /** 메뉴 유형 */
    public static class MENU_TYPE {
        /** 관리자 */
        public final static String ADMIN = "ADMIN";
        
        /** 프런트 */
        public final static String FRONT = "FRONT";
        
        /** API */
        public final static String API = "API";
    }
    
    /** 메뉴 레벨 유형 */
    public static class MENU_LEVEL_TYPE {
        /** 레벨1 */
        public final static String LEVEL1 = "1";
        
        /** 레벨2 */
        public final static String LEVEL2 = "2";
        
        /** 레벨3 */
        public final static String LEVEL3 = "3";
    }
    
    /** 메뉴 그룹 유형 */
    public static class MENU_GROUP_ID {
        /** 마스터 */
        public final static String MG001 = "MG001";
        
        /** 서브 마스터 */
        public final static String MG002 = "MG002";
        
        /** 슈퍼 루키 */
        public final static String MG003 = "MG003";
        
        /** 루키 */
        public final static String MG004 = "MG004";
    }
    
    /** REQ METHOD 유형 */
    public static class REQ_METHOD_TYPE {
        /** GET */
        public final static String GET = "GET";
        
        /** POST */
        public final static String POST = "POST";
        
        /** PUT */
        public final static String PUT = "PUT";
        
        /** DELETE */
        public final static String DELETE = "DELETE";
    }
    
    /** 전송 유형 */
    public static class SENDER_TYPE {
        /** 메일 */
        public final static String MAIL = "MAIL";
        
        /** SMS */
        public final static String SMS = "SMS";
        
        /** 알림톡 */
        public final static String TALK = "TALK";
        
        /** 푸시 */
        public final static String PUSH = "PUSH";
    }
    
    /** 게시판 종류 */
    public static class BOARD_TYPE {
        /** 공지 사항 */
        public final static String NOTICE = "NOTICE";
        
        /** FAQ */
        public final static String FAQ = "FAQ";
        
        /** QNA */
        public final static String QNA = "QNA";
    }
    
    /** 게시판 권한 유형 */
    public static class BOARD_AUTH_TYPE {
        /** 전체 */
        public final static String ALL = "ALL";
        
        /** LOGIN */
        public final static String LOGIN = "LOGIN";
    }
    
    /** 공지 등록 유형 */
    public static class NOTICE_REG_TYPE {
        /** 대기 */
        public final static String WAIT = "WAIT";
        
        /** 등록 */
        public final static String REGIST = "REGIST";
        
        /** 등록+공지 */
        public final static String NOTICE = "NOTICE";
    }
    
    /** 다운로드 파일 유형 */
    public static class DOWN_FILE_TYPE {
        /** EXCEL */
        public final static String EXCEL = "EXCEL";
        
        /** CSV */
        public final static String CSV = "CSV";
        
        /** PDF */
        public final static String PDF = "PDF";
    }
    
    /** 입력 유형 */
    public static class INPUT_TYPE {
        /** 숫자 */
        public final static String NUMBER = "NUMBER";
        
        /** 숫자 */
        public final static String NUMERIC = "NUMERIC";
        
        /** 이메일 */
        public final static String EMAIL = "EMAIL";
        
        /** 휴대폰번호 */
        public final static String MOBILE = "MOBILE";
        
        /** 전화번호 */
        public final static String TEL = "TEL";
        
        /** 우편번호 */
        public final static String ZIP = "ZIP";
        
        /** 연도 */
        public final static String YEAR = "YEAR";
        
        /** 월 */
        public final static String MONTH = "MONTH";
        
        /** 일시 */
        public final static String DATETIME = "DATETIME";
        
        /** 날짜 */
        public final static String DATE = "DATE";
        
        /** 시간AMPM */
        public final static String AMPM = "AMPM";
        
        /** 시간 */
        public final static String TIME = "TIME";
        
        /** 배열 */
        public final static String ARRAY = "ARRAY";
        
        /** UUID */
        public final static String UUID = "UUID";
    }
    
    /** 인증 유형 */
    public static class AUTH_TYPE {
        /** 가입 */
        public final static String JOIN = "JOIN";
        
        /** 전환 가입 */
        public final static String CHANGE = "CHANGE";
        
        /** ID */
        public final static String ID = "ID";
        
        /** 비밀번호 */
        public final static String PASSWORD = "PASSWORD";
        
        /** 전화번호 */
        public final static String PHONE = "PHONE";
    }
    
    /** 사용자 유형 */
    public static class USER_TYPE {
        /** 비회원 */
        public final static String UN = "UN";
        
        /** 간편 가입 */
        public final static String NORMAL = "NORMAL";
        
        /** 카카오 */
        public final static String KAKAO = "KAKAO";
        
        /** 페이스북 */
        public final static String FACEBOOK = "FACEBOOK";
        
        /** 애플 */
        public final static String APPLE = "APPLE";
    }
    
    /** 사용자 상태 */
    public static class USER_STATUS {
        /** 정상 */
        public final static String NORMAL = "NORMAL";
        
        /** 정지 */
        public final static String SUSPENSION = "SUSPENSION";
        
        /** 휴면 */
        public final static String DORMANCY = "DORMANCY";
        
        /** 해지 */
        public final static String QUIT = "QUIT";
        
        /** 휴면 안내 */
        public final static String DORMANCY_NOTICE = "DORMANCY_NOTICE";
    }
    
    /** 회원 관리 유형 */
    public static class USER_MANAGEMENT_TYPE {
        /** 일반 */
        public final static String NORMAL = "NORMAL";
        
        /** Black */
        public final static String BLACK = "BLACK";
        
        /** Red */
        public final static String RED = "RED";
    }
    
    /** 탈퇴 유형 */
    public static class QUIT_TYPE {
        /** 유저 탈퇴 */
        public final static String USER = "USER";
        
        /** 관리자 탈퇴 */
        public final static String ADMIN = "ADMIN";
    }
    
    /** OS 유형 */
    public static class OS_TYPE {
        /** 안드로이드 */
        public final static String ANDROID = "Android";
        
        /** IOS */
        public final static String IOS = "iOS";
    }
    
    /** 메일 유형 */
    public static class MAIL_TYPE {
        /** 아이디 찾기 */
        public final static String FIND_ID = "FIND_ID";
        
        /** 비밀번호 찾기 */
        public final static String FIND_PASSWORD = "FIND_PASSWORD";
        
        /** 인증번호 */
        public final static String AUTH = "AUTH";
    }
    
    /** 사용자 비밀번호 초기화 유형 */
    public static class USER_PASS_INIT_TYPE {
        /** 사용자 초기화 */
        public final static String INIT_USER = "INIT_USER";
        
        /** 관리자 초기화 */
        public final static String INIT_ADMIN = "INIT_ADMIN";
        
        /** 갱신 */
        public final static String UPDATE = "UPDATE";
    }
    
    /** 약관 유형 */
    public static class TERMS_TYPE {
        /** 이용 약관 */
        public final static String TERMS_SERVICE = "TERMS_SERVICE";
        
        /** 개인 정보 제 3 자 제공에 따른 동의 */
        public final static String PRIVACY_APPROVAL = "PRIVACY_APPROVAL";
        
        /** 개인 정보 처리 방침 */
        public final static String PRIVACY_POLICY = "PRIVACY_POLICY";
    }
    
    /** 질문 유형 */
    public static class QUESTION_TYPE {
        /** 루픽 문의 */
        public final static String LUPICK = "LUPICK";
        
        /** 이벤트 문의 */
        public final static String EVENT = "EVENT";
    }
    
    /** 피드 유형 */
    public static class FEED_TYPE {
        /** 이벤트 후기 */
        public final static String EVENT_REVIEW = "EVENT_REVIEW";
        
        /** 루픽 스토리 */
        public final static String LUPICK_STORY = "LUPICK_STORY";
        
        /** 기획전 */
        public final static String PLANNING = "PLANNING";
    }
    
    /** 메일 탬플릿 카테고리 유형 */
    public static class MAIL_TEMPLETE_CATE_TYPE {
        /** 이메일 인증번호 전송 */
        public final static String CERTIFICATION = "CERTIFICATION";
        
        /** 회원가입 완료 */
        public final static String WELCOME = "WELCOME";
        
        /** 휴면전환 안내 */
        public final static String DORMANCY = "DORMANCY";
        
        /** 계정삭제 안내 */
        public final static String USER_DELETE = "USER_DELETE";
        
        /** 탈퇴 완료 */
        public final static String WITHDRAW = "WITHDRAW";
        
        /** 주문 완료 */
        public final static String ORDER_COMPLETE = "ORDER_COMPLETE";
        
        /** 주문 취소 */
        public final static String ORDER_CANCEL = "ORDER_CANCEL";
        
        /** 구매확정 안내 */
        public final static String PURCHASE_OK = "PURCHASE_OK";
        
        /** 자동구매확정 */
        public final static String PURCHASE_CONFIRM = "PURCHASE_CONFIRM";
        
        /** 영수증 발급 */
        public final static String RECEIPT = "RECEIPT";
        
        /** 문의 답변등록 */
        public final static String QNA_REPLY = "QNA_REPLY";
        
        /** 서비스 관련 고지 */
        public final static String TERMS = "TERMS";
        
        /** 관리자 전송 */
        public final static String ADMIN = "ADMIN";
    }
    
    /** SMS 탬플릿 카테고리 유형 */
    public static class SMS_TEMPLETE_CATE_TYPE {
        /** 인증 */
        public final static String CERTIFICATION = "CERTIFICATION";
        
        /** 회원가입 축하 알림 */
        public final static String USER_JOIN = "USER_JOIN";
        
        /** 일반상품 주문완료 알림 */
        public final static String ORDER_COMPLETE = "ORDER_COMPLETE";
        
        /** 일반상품 주문취소 알림 */
        public final static String ORDER_CANCEL = "ORDER_CANCEL";
        
        /** 이벤트 성공 알림 */
        public final static String EVENT_SUCCESS = "EVENT_SUCCESS";
        
        /** 이벤트 무산 알림 */
        public final static String EVENT_FAILURE = "EVENT_FAILURE";
        
        /** 배송시작 알림 */
        public final static String DELIVERY_START = "DELIVERY_START";
        
        /** 배송완료 알림 */
        public final static String DELIVERY_END = "DELIVERY_END";
        
        /** 구매 확정 리마인드 알림 */
        public final static String PURCHASE_OK = "PURCHASE_OK";
        
        /** 투표 시작 알림 */
        public final static String VOTE = "VOTE";
        
        /** 투표 결과 알림 */
        public final static String VOTE_RESULT = "VOTE_RESULT";
        
        /** 포인트 소멸 안내 알림 */
        public final static String POINT_DEAD = "POINT_DEAD";
        
        /** 휴면전환 안내 알림 */
        public final static String DORMANCY = "DORMANCY";
        
        /** 계정삭제 안내 알림 */
        public final static String USER_DELETE = "USER_DELETE";
        
        /** 관리자 전송 */
        public final static String ADMIN = "ADMIN";
    }
    
    /** 푸시 탬플릿 카테고리 유형 */
    public static class PUSH_TEMPLETE_CATE_TYPE {
        /** 예약 이벤트 시작 시 */
        public final static String EVENT = "EVENT";
        
        /** 이벤트 성공 시 */
        public final static String EVENT_SUCCESS = "EVENT_SUCCESS";
        
        /** 이벤트 실패 시 */
        public final static String EVENT_FAILURE = "EVENT_FAILURE";
        
        /** 배송 시작 정보 */
        public final static String DELIVERY_START = "DELIVERY_START";
        
        /** 구매확정 리마인드 */
        public final static String PURCHASE_OK = "PURCHASE_OK";
        
        /** 투표 시작 */
        public final static String VOTE = "VOTE";
        
        /** 투표 결과 */
        public final static String VOTE_RESULT = "VOTE_RESULT";
        
        /** 참여했던 이벤트 피드 업로드 */
        public final static String EVENT_FEED = "EVENT_FEED";
        
        /** 찜한픽 이벤트 마감 임박 */
        public final static String EVENT_END = "EVENT_END";
        
        /** 프로모션 알림 */
        public final static String PROMOTION = "PROMOTION";
        
        /** 관리자 전송 */
        public final static String ADMIN = "ADMIN";
    }
    
    /** 푸시 카테고리 유형 */
    public static class PUSH_CATE_TYPE {
        /** 이벤트 알림 */
        public final static String EVENT = "EVENT";
        
        /** 배송 알림 */
        public final static String DELIVERY = "DELIVERY";
        
        /** 투표 알림 */
        public final static String VOTE = "VOTE";
        
        /** 루픽 알림 */
        public final static String LUPICK = "LUPICK";
    }
    
    /** 승인유형 */
    public static class APPROVAL_TYPE {
        /** 승인완료 */
        public final static String APPROVED = "APPROVED";
        
        /** 승인대기 */
        public final static String WAITING = "WAITING";
        
        /** 승인반려 */
        public final static String REJECTION = "REJECTION";
    }
    
    /** 배송유형 */
    public static class DELIVERY_TYPE {
        /** 기본 배송 */
        public final static String STANDARD = "STANDARD";
        
        /** 수량별 배송비 */
        public final static String BY_QUANTITY = "BY_QUANTITY";
        
        /** 조건부 무료배송 */
        public final static String CONDITIONAL = "CONDITIONAL";
        
        /** 착불 배송 */
        public final static String ON_DELIVERY = "ON_DELIVERY";
    }
    
    /** 이벤트 상태 */
    public static class EVENT_STATUS {
        /** 진행중인 이벤트 */
        public final static String ING = "ING";
        
        /** 대기중인 이벤트 */
        public final static String WAITING = "WAITING";
        
        /** 마감된 이벤트 */
        public final static String CLOSED = "CLOSED";
    }
    
    /** 이벤트 앱 상태 및 어드민 마감 상세 상태 (EVENT_STATUS가 진행중/마감인 경우에만 해당) */
    /** 참고: Admin에서는 이벤트가 마감된 경우에 "이벤트 성공", "이벤트 성공 (종료)", "이벤트 실패"가 표시됨. 표시 순서: "이벤트 성공" > "이벤트 성공 (종료)" > "이벤트 실패" */
    public static class EVENT_APP_STATUS {
        /** 이벤트 진행 중 */
        /** (EVENT_STATUS가 ING인 경우) 이벤트 기간 동안 참여율 100% 미만인 경우 */
        public final static String ING = "ING";
        
        /** 이벤트 성공 */
        /** (EVENT_STATUS가 CLOSED인 경우) 이벤트 기간 내 참여율 100%로 이벤트 모집은 마감되었으나, 아직 루피커 선정 및 결재승인을 받지 않은 이벤트 */
        public final static String CLOSED_SUCCESS = "CLOSED_SUCCESS";
        
        /** 이벤트 성공 (종료) */
        /** (EVENT_STATUS가 CLOSED인 경우) 이벤트 기간 내 참여율 100%로 이벤트 모집 마감, 루피커 선정, 결재승인까지 모두 완료된 이벤트 */
        public final static String CLOSED_END = "CLOSED_END";
        
        /** 이벤트 실패 */
        /** (EVENT_STATUS가 CLOSED인 경우) 이벤트 기간 내 참여율 100% 미만으로 종료된 이벤트 */
        public final static String CLOSED_FAILURE = "CLOSED_FAILURE";
    }
    
    /** 럭키상품유형 */
    public static class LUCKYITEM_TYPE {
        /** 메인 */
        public final static String MAIN = "MAIN";
        
        /** 상품 */
        public final static String PRODUCT = "PRODUCT";
        
        /** 포인트 지급 */
        public final static String POINT = "POINT";
    }
    
    /** 주문상태 */
    public static class ORDER_STATUS {
        /** 배송 완료 */
        public final static String DELIVERY_COMPLETE = "DELIVERY_COMPLETE";
        
        /** 배송 중 */
        public final static String DELIVERY_ING = "DELIVERY_ING";
        
        /** 결제 취소 */
        public final static String PAY_CANCEL = "PAY_CANCEL";
        
        /** 결제 완료 */
        public final static String PAY_COMPLETE = "PAY_COMPLETE";
        
        /** 상품 준비 중 */
        public final static String PRODUCT_PREPARE = "PRODUCT_PREPARE";
        
        /** 구매 확정 */
        public final static String PURCHASE_CONFIRM = "PURCHASE_CONFIRM";
        
        /** 환불/반품 */
        public final static String REFUND = "REFUND";
        
        /** 결제 대기 */
        public final static String PAY_STANDBY = "PAY_STANDBY";
    }
    
    /** 임시저장 유무 */
    public static class TMPSAVE_YN {
        /** 임시저장 */
        public final static String Y = "Y";
        
        /** 저장 */
        public final static String N = "N";
    }
    
    /** 메인이벤트 메인표시유무 */
    public static class MAIN_DISP_YN {
        /** 메인표시 */
        public final static String Y = "Y";
        
        /** 메인미표시 */
        public final static String N = "N";
    }
    
    /** 이벤트 결재 상태 유무 */
    public static class EVENT_APPROVAL_YN {
        /** 결재완료 */
        public final static String Y = "Y";
        
        /** 결재요청 */
        public final static String N = "N";
    }
        
    /** 포인트유형 */
    public static class POINT_TYPE {
        /** 차감 (-) */
        public final static String MINUS = "MINUS";
        
        /** 지급 (+) */
        public final static String PLUS = "PLUS";
    }
    
    /** 성별유형 */
    public static class GENDER_TYPE {
        /** 남성 */
        public final static String MALE = "MALE";
        
        /** 여성 */
        public final static String FEMALE = "FEMALE";
        
        /** 선택안함 */
        public final static String NONE = "NONE";
    }
    
    /** 수동유무 */
    public static class MANUAL_YN {
        /** 수동(직접) */
        public final static String Y = "Y";
        
        /** 자동(랜덤) */
        public final static String N = "N";
    }
    
    /** 당첨유무 */
    public static class WIN_YN {
        /** 당첨 */
        public final static String Y = "Y";
        
        /** 낙첨 */
        public final static String N = "N";
    }
    
    /** 확정유무 */
    public static class CONFIRM_YN {
        /** 확정 */
        public final static String Y = "Y";
        
        /** 미확정 */
        public final static String N = "N";
    }
    
    /** 구매가능 유무 */
    public static class ORDER_POSSIBLE_YN {
        /** 가능(픽) */
        public final static String Y = "Y";
        
        /** 불가능(결제정보) */
        public final static String N = "N";
    }
    
    /** 참여 유무 */
    public static class ENTER_YN {
        /** 참여 */
        public final static String Y = "Y";
        
        /** 미참여 */
        public final static String N = "N";
    }
    
    /** 포인트 사유 */
    public static class POINT_REASON_TYPE {
        /** 상품 구매시 사용 */
        public final static String PRODUCT_PURCHASE = "PRODUCT_PURCHASE";
        
        /** 포인트 환원 (취소/환불) */
        public final static String POINT_RESTORATION = "POINT_RESTORATION";
        
        /** 루피커 선정 포인트 지급 */
        public final static String LUPICK_SELECTION = "LUPICK_SELECTION";
        
        /** 기타 */
        public final static String DELIVERY_ING = "DELIVERY_ING";
        
        /** 배송 완료 */
        public final static String ETC = "ETC";
        
        /** 루픽 이벤트 당첨 적립 */
        public final static String WINNING_POINT = "WINNING_POINT";
        
        /** 일반상품 결제 시 사용 */
        public final static String ITEM_PAY = "ITEM_PAY";
        
        /** 포인트 소멸 */
        public final static String DEAD_POINT = "DEAD_POINT";
    }
    
    /** 이미지 유형 */
    public static class IMG_TYPE {
        /** 이미지 */
        public final static String IMAGE = "IMAGE";
        
        /** 영상 */
        public final static String VIDEO = "VIDEO";
    }
    
    /** 배치 카테고리 유형 */
    public static class BATCH_CATE_TYPE {
        /** 접속자 수 데이터 쌓기 */
        public final static String REPORT_NOW_CONN = "REPORT_NOW_CONN";
        
        /** 총 가입자 */
        public final static String REPORT_TOTAL_JOIN = "REPORT_TOTAL_JOIN";
        
        /** 오늘 매출 금액 */
        public final static String REPORT_TODAY_PRICE = "REPORT_TODAY_PRICE";
        
        /** 오늘 주문 수 */
        public final static String REPORT_TODAY_ORDER = "REPORT_TODAY_ORDER";
        
        /** 오픈 대기 중, 진행 중 */
        public final static String REPORT_NOW_EVENT = "REPORT_NOW_EVENT";
        
        /** 매시 집계 */
        public final static String REPORT_HOUR = "REPORT_HOUR";
        
        /** 일단위 집계 */
        public final static String REPORT_DATE = "REPORT_DATE";
        
        /** 월단위 집계 */
        public final static String REPORT_MONTH = "REPORT_MONTH";
        
        /** 이벤트 시작 */
        public final static String EVENT_START = "EVENT_START";
        
        /** 이벤트 마감 임박 */
        public final static String EVENT_DEADLINE = "EVENT_DEADLINE";
        
        /** 이벤트 성공 */
        public final static String EVENT_SUCCESS = "EVENT_SUCCESS";
        
        /** 이벤트 실패 */
        public final static String EVENT_FAILURE = "EVENT_FAILURE";
        
        /** 기준에 의해 구매 확정 안내 */
        public final static String PURCHASE_OK = "PURCHASE_OK";
        
        /** 기준에 의해 자동 구매 확정 */
        public final static String PURCHASE_CONFIRM = "PURCHASE_CONFIRM";
        
        /** 기준에 의해 소멸 포인트 확정 안내 */
        public final static String NOTICE_POINT_DEAD = "NOTICE_POINT_DEAD";
        
        /** 기준에 의해 소멸 포인트 갱신 */
        public final static String POINT_DEAD = "POINT_DEAD";
        
        /** 기준에 의해 휴면 안내 */
        public final static String NOTICE_DORMANCY = "NOTICE_DORMANCY";
        
        /** 기준에 의해 자동 삭제 안내 */
        public final static String NOTICE_DELETE = "NOTICE_DELETE";
        
        /** 기준에 의해 자동 삭제 */
        public final static String USER_DELETE = "USER_DELETE";
        
        /** 서비스 관련 고지 */
        public final static String NOTICE_SERVICE = "NOTICE_SERVICE";
        
        /** 투표 */
        public final static String VOTE = "VOTE";
        
        /** 투표 결과 집계 */
        public final static String VOTE_RESULT = "VOTE_RESULT";
    }
    
    /** SMS 유형 (SMS 0/FAX 2/PHONE 3/MMS 5/AT 6/FT 7/RCS 8/BI 11/BW 12) */
    public static class SMS_TYPE {
        /** SMS */
        public final static int SMS = 0;
        
        /** FAX */
        public final static int FAX = 2;
        
        /** PHONE */
        public final static int PHONE = 3;
        
        /** MMS */
        public final static int MMS = 5;
        
        /** AT */
        public final static int AT = 6;
        
        /** FT */
        public final static int FT = 7;
        
        /** RCS */
        public final static int RCS = 8;
        
        /** BI */
        public final static int BI = 11;
        
        /** BW */
        public final static int BW = 12;
    }
    
    /** 이벤트 주제 태그 */
    public static class TAG {
        /** 태그 길이 제한 */
        public final static int TAG_LENGTH_LIMIT = 15;
    }
    
    /** 성공 유무 */
    public static class SUCCESS_YN {
        /** 성공 */
        public final static String Y = "1";
        
        /** 실패 */
        public final static String N = "0";
    }
    
    /** 예약 유무 */
    public static class RESERVE_YN {
        /** 예약 */
        public final static String Y = "Y";
        
        /** 미예약 */
        public final static String N = "N";
    }
    
    /** 결제 방법 유형 */
    public static class PAY_METHOD_TYPE {
        /** 신용카드 */
        public final static String CREDITCARD = "CREDITCARD";
        
        /** 휴대폰결제 */
        public final static String MOBILE = "MOBILE";
        
        /** 네이버페이 */
        public final static String NAVER = "NAVER";
        
        /** 카카오페이 */
        public final static String KAKAO = "KAKAO";
        
        /** 페이코 */
        public final static String PAYCO = "PAYCO";
        
        /** 토스 */
        public final static String TOSS = "TOSS";
    }
    
    /** 마케팅 동의 유무 */
    public static class MARKETING_YN {
        /** 마케팅 동의 : Y */
        public final static String Y = "Y";
        /** 마케팅 동의 : N */
        public final static String N = "N";
    }
    
    /** 발송 유무 */
    public static class SEND_YN {
        /** 발송 : Y */
        public final static String Y = "Y";
        /** 미발송 : N */
        public final static String N = "N";
        
        /** 발송실패 : F */
        public final static String F = "F";
    }
}