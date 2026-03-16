package com.app.datwdt.constants;

import android.os.Environment;

public class Constants {

    public final static String MESSAGE_TYPE = "TEXT";


    public final static int DEFAULT_ZERO = 0;
    public final static int DEFAULT_ONE = 1;

    /**
     * RUN TIME PERMISSION REQUEST CODES
     */
    public static final int REQUEST_CODE_CAMERA_PERMISSION = 110;
    public static final int REQUEST_CODE_STORAGE_PERMISSION = 111;
    public static final int REQUEST_CODE_CALL_PERMISSION = 333;
    public static final int REQUEST_CODE_LOCATION = 333;
    public static final int FILE_REQUEST_CODE = 100;

    /**
     * START ACTIVITY FOR RESULT CODE
     */
    public static final int REQUEST_CODE_SEARCH_CONTACTS = 501;

    /**
     * PERMISSION RESULT CODE
     */
    public static final int REQUEST_CODE_PERMISSION_RESULT = 600;
    public static final int REQUEST_CODE_CAMERA_STORAGE_RESULT = 601;

    /**
     * FRAGMENT FLAGS AND TAGS
     */
    public static final String START_DRAWER_FRAGMENT_TAG = "StartDrawerFragment";
    public static final int START_DRAWER_FRAGMENT_FLAG = 1;

    /**
     * DATE/TIME FORMAT
     */
    public static final String DATE_SOURCE_FORMAT = "yyyy/MM/dd";
    public static final String DATE_DD_MM_YYYY_FORMAT = "dd/MM/yyyy";
    public static final String DATE_DD_MMM_YYYY_FORMAT = "dd MMM yyyy";
    public static final String DATE_DD_MM_YYYY_HH_MM_AA_FORMAT = "dd/MM/yyyy hh:mm a";
    public static final String DATE_DD_MM_YYYY_HH_MM_AA_FORMAT_ = "dd MMM yyyy hh:mm aa";
    public static final String DATE_YYYY_MM_DD_HH_MM_AA_FORMAT = "yyyy/MM/dd HH:mm:ss";
    public static final String DATE_YYYY_MM_DD_FORMAT = "yyyy-MM-dd";
    public static final String DATE_YYYY_MM_DD_HH_FORMAT = "yyyy-MM-dd HH";
    public static final String DATE_D_MMMM_FORMAT = "d MMMM";
    public static final String DATE_HH_FORMAT = "HH";
    public static final String TIME_HH_MM_SS_FORMAT = "HH:mm:ss";
    public static final String TIME_HH_AA_DD_MMMM_FORMAT = "ha 'on' dd MMMM yyyy";
    public static final String TIME_HH_MM_A_FORMAT = "hh:mm a";
    public static final String DATE_PUSH_NOTIFICATION_FORMAT = "HHmmssSSS";
    public static final String IMAGE_FILE_NAME_PREFIX = "IMG_CAM" + "_X" + ".jpg";
    public static final String VIDEO_FILE_NAME_PREFIX = "VID_CAM" + "_X" + ".jpg";
    public static final String AUDIO_NAME_PREFIX = "AUDIO" + "_X" + ".mp3";
    public static final int FLAG_CROP = 1314;
    public static final String DATE_FILE_FORMAT = "HHmmssSSS";

    /**
     * INTENT FLAGS
     */
    public static String from = "from";
    public static String from_push = "from_push";

    public static int from_add = 1;
    public static int from_edit = 2;

    public static String from_registration = "from_registration";
    public static String from_login = "from_login";

    /**
     * ITEM CLICKS FLAGS FOR INTERACT
     */
    public static final int ITEM_CLICK = 0;
    public static final int ITEM_CLICK_CATEGORY = 60;
    public static final int ITEM_CLICK_PRODUCT = 61;
    public static final int ITEM_CLICK_DELETE = 62;

    /**
     * DEVICE TYPE PASS IN API
     */
    public static final String DEVICE_TYPE_ANDROID = "0";
    //default list load time duration
    public static final int TIME_DURATION = 3000;
    public static final String offset = "offset";
    public static final int FROM_NORMAL = 0;
    public static final int FROM_PULL_REFRESH = 1;

    /**
     * USER DATA
     */

    public static final String user_id = "user_id";
    public static final String access_token = "access_token";
    public static final String auth_token = "auth_token";
    public static final String Authorization = "Authorization";
    public static final String DATA = "data";
    public static final String image_id = "image_id";
    public static final String mood_rate = "mood_rate";
    public static final String mood_msg = "mood_msg";
    public static final String mood_json = "mood_json";

    /**
     * API parameters
     */
    public static final String name = "name";
    public static final String phone = "phone";

    public static final String about = "about";
    public static final String country_code = "country_code";
    public static final String mobile_no = "mobile_no";

    public static final String emoji_id = "emoji_id";
    public static final String mood_score = "mood_score";
    public static final String is_send_mail = "is_send_mail";
    public static final String is_like = "is_like";
    public static final String mood_date = "mood_date";
    public static final String mood_time = "mood_time";
    public static final String mood_description = "mood_description";
    public static final String description = "description";
    public static final String start_date = "start_date";
    public static final String end_date = "end_date";
    public static final String contact_id = "contact_id";
    public static final String contact_data = "contact_data";
    public static final String conatct_is_edit = "conatct_is_edit";

    /**
     * DEFAULT CONSTANTS
     */
    public static final int MINUS_ONE = -1;
    public static final int DESIGN_IMAGE_RADIUS = 10;

    /**
     * Language localisation
     */
    public static String language = "language";
    public static String language_en = "en";
    public static String language_english = "en";
    public static String language_ar = "ar";
    public static String language_arabic = "ar";

    /**
     * IMAGE/VIDEO
     */
    public static String APP_ROOT_FOLDER = Environment
            .getExternalStorageDirectory()
            .getAbsolutePath() + "/" + "Mentabee" + "/";
    public static final String IMAGE_ROOT_FOLDER = APP_ROOT_FOLDER + "IMAGES";
    public static final String APP_DOWNLOADS_FOLDER = APP_ROOT_FOLDER + "MEDIA";
    public static final String AUDIO_ROOT_FOLDER = APP_ROOT_FOLDER + "AUDIO";

    /**
     * CROP
     */
    public static String image = "image";
    public static String video = "video";
    public static String video_url = "video_url";
    public static String FLAG_IS_SQUARE = "flag_is_square";
    public static int IMAGE_RESIZE_WIDTH = 500;
    public static int IMAGE_RESIZE_HEIGHT = 500;
    public static int IMAGE_RESIZE_HEIGHT_NEW = 150;
    public static int IMAGE_RESIZE_WIDTH_NEW = 150;

    /**
     * FCM PUSH NOTIFICATION
     */
    public static String fcm_registration_id = "fcm_registration_id";
    public static String device_id = "device_token";
    public static String device_type = "device_type";
    public static String device_type_value = "android";
    public static String register_id = "register_id";
    public static String push_type = "push_type";
    public static String lang = "lang";
    public static String title = "title";
    public static String body = "body";
    public static String badge = "badge";

    /**
     * API RESPONSE FLAGS
     */
    public static int RESPONSE_FAILURE_FLAG = 0;
    public static int RESPONSE_SUCCESS_FLAG = 1;
    public static int RESPONSE_LOGOUT_FLAG = 2;

    /**
     * PASSWORD LIMIT GLOBAL
     */
    public static int PASS_LIMIT = 6;
    public static final long SPLASH_WAIT = 3000;

    /**
     * LIST PAGINATION
     */
    public static int pagination_start_offset = 1;
    public static int pagination_last_offset = -1;

    public static final String stayEmail = "stayEmail";
    public static final String stayPass = "stayPass";

    //RUSOCIAL
    public static final String IMAGE_PREFIX_URL = "http://138.68.145.158/rusocial/app_images/user_profile/";


    public static final String verify_code = "verify_code";
    public static final String age = "age";
    public static final String bio = "bio";
    public static final String job_title = "job_title";
    public static final String min_age = "min_age";
    public static final String max_age = "max_age";
    public static final String verify_from = "verify_from";

    public static final String latitude = "latitude";
    public static final String longitude = "longitude";
    public static final String page = "page";
    public static final String limit = "limit";
    public static final String user_data = "user_data";
    public static final String opo_user_token = "opo_user_token";
    public static final String message = "message";
    public static final String request_id = "request_id";
    public static final String request = "request";
    public static final String block_user_token = "block_user_token";
    public static final String block = "block";
    public static final String report_user_token = "report_user_token";
    public static final String type = "type";
    public static final String is_snooze = "is_snooze";
    public static final String files = "files";
    public static final String file = "file";
    public static final String user_images_id = "user_images_id";
    public static final String driving_mode = "driving_mode";
    //    public static final String min_age = "min_age";
//    public static final String max_age = "max_age";
    public static final String file1 = "file1";
    public static final String file2 = "file2";
    public static final String old_password = "old_password";
    public static final String new_password = "new_password";
    public static final String device_token = "device_token";
    public static final String receiver_token = "receiver_token";
    public static final String chat_date = "chat_date";
    public static final String message_type = "message_type";
    public static final String chat_data = "chat_data";
    public static final String is_video = "is_video";

    public static String CONVERSATION_NORMAL = "NORMAL";


    public static final String is_purchased = "is_purchased";
    public static final String is_other_purchased = "is_other_purchased";
    public static final String is_android_purchased = "is_android_purchased";
    public static final String purchase_start_date_ms = "purchase_start_date_ms";
    public static final String purchase_end_date_ms = "purchase_end_date_ms";
    public static final String original_transaction_id = "original_transaction_id";
    public static final String receiptUrl = "receiptUrl";
    public static final String packageName = "packageName";
    public static final String productId = "productId";
    public static final String purchaseToken = "purchaseToken";
    public static final String product_identifier = "product_identifier";


    //    DATW APP CONSTANTS

    public static String RESPONSE_SUCCESS = "true";
    public static String LAST_LOADED_FRAGMENT = "LAST_LOADED_FRAGMENT";



    public static final String user_name = "user_name";
    public static final String password = "password";
    public static final String user_gson = "user_gson";
    public static final String user_role = "user_role";
    public static final String role_admin = "admin";
    public static final String otp_code = "otp_code";
    public static final String entity_id = "entity_id";
    public static final String user_pass = "user_pass";
    public static final String email = "email";
    public static final String qf_ff_number = "qf_ff_number";
    public static final String phone_number = "phone_number";
    public static final String dob = "dob";
    public static final String address = "address";
    public static final String group_nm = "group_nm";
    public static final String group_id = "group_id";
    public static final String file_id = "file_id";
    public static final String group_name = "group_name";

}