package com.techiedb.app.bookman.controllers;

/**
 * Copyright (C) 2014 Techie Digital Benchwork Inc. All rights reserved.
 * Mobile UX Promotion Division.
 * This software and its documentation are confidential and proprietary
 * information of Techie Digital Benchwork Inc.  No part of the software and
 * documents may be copied, reproduced, transmitted, translated, or reduced to
 * any electronic medium or machine-readable form without the prior written
 * consent of Techie Digital Benchwork.
 * Techie Digital Benchwork makes no representations with respect to the contents,
 * and assumes no responsibility for any errors that might appear in the
 * software and documents. This publication and the contents hereof are subject
 * to change without notice.
 * History
 *
 * @author Larry Pham
 * @since 2014.10.02
 */
public class ControllerMessage {
    public static final int BASE_CONTROL_MESSAGE = 100;
    public static final int REQUEST_OWNER_MESSAGE = BASE_CONTROL_MESSAGE + 1;
    public static final int REQUEST_DOWNLOAD_IMAGE = BASE_CONTROL_MESSAGE + 12;

    public static final int LOAD_MARKET_GALLERY_EBOOK = BASE_CONTROL_MESSAGE + 20;
    public static final int LOAD_MARKET_GALLERY_EBOOK_COMPLETED = BASE_CONTROL_MESSAGE + 21;
    public static final int ADD_MARKET_READ_ITEM = BASE_CONTROL_MESSAGE + 22;

    public static final int LOADING_GALLERY_EBOOK_IMAGE = BASE_CONTROL_MESSAGE + 30;
    public static final int LOADING_GALLERY_EBOOK_IMAGE_COMPLETED = BASE_CONTROL_MESSAGE + 31;

    public static final int REQUEST_TO_SERVER_BASE = 200;
    public static final int REQUEST_TO_SERVER_SUMMARY = REQUEST_TO_SERVER_BASE + 1;
    public static final int REQUEST_TO_SERVER_CATEGORY_LIST = REQUEST_TO_SERVER_BASE + 2;
    public static final int REQUEST_TO_SERVER_KEYWORD = REQUEST_TO_SERVER_BASE + 3;
    public static final int REQUEST_TO_SERVER_NOTICE_LIST = REQUEST_TO_SERVER_BASE + 4;

    public static final int REQUEST_TO_SERVER_EBOOK_LIST_BY_CATEGORY = REQUEST_TO_SERVER_BASE + 5;
    public static final int REQUEST_TO_SERVER_EBOOK_LIST_BY_AUTHOR = REQUEST_TO_SERVER_BASE + 6;
    public static final int REQUEST_TO_SERVER_EBOOK_LIST_BY_PUBLISHER = REQUEST_TO_SERVER_BASE + 7;
    public static final int REQUEST_TO_SERVER_EBOOK_LIST_BY_RELEVANT = REQUEST_TO_SERVER_BASE + 8;
    public static final int REQUEST_TO_SERVER_EBOOK_LIST_BY_RECOMMENDED = REQUEST_TO_SERVER_BASE + 9;
    public static final int REQUEST_TO_SERVER_EBOOK_LIST_BY_KEYWORD = REQUEST_TO_SERVER_BASE + 10;


    public static final int REQUEST_TO_SUBSCRIBE = 300;
    public static final int REQUEST_TO_SUBSCRIBE_EBOOK_FAVOURITE = REQUEST_TO_SUBSCRIBE + 1;
    public static final int REQUEST_TO_SUBSCRIBE_EBOOK_AUTHORITIES_FAVOURITE = REQUEST_TO_SUBSCRIBE + 2;
    public static final int REQUEST_TO_SUBSCRIBE_EBOOK_PUBLISHER_FAVOURITE = REQUEST_TO_SUBSCRIBE + 3;
    public static final int REQUEST_TO_SUBSCRIBE_EBOOK_RELEVANT_FAVOURITE = REQUEST_TO_SUBSCRIBE + 4;

    public static final int REQUEST_TO_SERVER_ERROR_BASE = 400;
    public static final int REQUEST_TO_SERVER_ERROR = REQUEST_TO_SERVER_ERROR_BASE + 1;
    public static final int REQUEST_ERROR_REASON_INTERNAL_SERVER_ERROR = REQUEST_TO_SERVER_ERROR_BASE + 2;
    public static final int REQUEST_ERROR_REASON_UNKNOWN_HOST = REQUEST_TO_SERVER_ERROR_BASE + 3;


    public static final int REQUEST_TO_SERVER_REASON_BASE = 450;
    public static final int REQUEST_ERROR_REASON_PARAMETER_MISSING = REQUEST_TO_SERVER_REASON_BASE + 1;
    public static final int REQUEST_ERROR_REASON_HTTP_CONNECT_FAIL = REQUEST_TO_SERVER_REASON_BASE + 2;
    public static final int REQUEST_ERROR_REASON_HTTP_AUTHORIZED = REQUEST_TO_SERVER_REASON_BASE + 3;
    public static final int REQUEST_ERROR_REASON_UNSUPPORTED_URL = REQUEST_TO_SERVER_REASON_BASE + 4;
    public static final int REQUEST_ERROR_REASON_JSON_PARSE_EXCEPTION = REQUEST_TO_SERVER_REASON_BASE + 5;
    public static final int REQUEST_ERROR_REASON_IMAGE_DOWNLOAD_ERROR = REQUEST_TO_SERVER_REASON_BASE + 6;
    public static final int REQUEST_ERROR_REASON_SERVER_CONNECT_TIMEOUT = REQUEST_TO_SERVER_REASON_BASE + 7;
    public static final int REQUEST_ERROR_REASON_XML_PARSE_EXCEPTION = REQUEST_TO_SERVER_REASON_BASE + 8;
    public static final int REQUEST_ERROR_REASON_SERVER_DATA_CORRUPTION = REQUEST_TO_SERVER_REASON_BASE + 13;

    public static final int REQUEST_EBOOK_IMAGE_FROM_SERVER_COMPLETED = 500;
    public static final int PAUSE_BACKGROUND_TASK = 350;
    public static final int RESUME_BACKGROUND_TASK = 352;
    public static final int DECODE_EBOOK_IMAGE_BITMAP = 351;

    public static final int ACTION_WAITING_START = 700;
    public static final int ACTION_WAITING_END = 701;
    public static final int AUTO_DELETE = 650;
    public static final int REQUEST_TO_SERVER_CANCEL = 800;
    public static final int NETWORK_DISCONNECT = 801;

    public static final int REGISTER_DEVICE_UUID_SETTINGS= 900;
    public static final int REGISTER_DEVICE_INFO_SETTINGS = 901;
    public static final int REQUEST_EBOOKS_LIST_REQUERY = 324;

    /**
     * Method toString
     * @param msg Integer Type.
     * @return String type. The message's short-description.
     */
    public static String toString(int msg){
        String result = null;
        switch (msg){
            case REQUEST_DOWNLOAD_IMAGE:{
                result = "REQUEST_DOWNLOAD_IMAGE";
                break;
            }
            case REQUEST_EBOOKS_LIST_REQUERY:{
                result = "REQUEST_EBOOKS_LIST_REQUERY";
                break;
            }
            case REQUEST_OWNER_MESSAGE:{
                result = "REQUEST_OWNER_MESSAGE";
                break;
            }
            case REQUEST_TO_SERVER_CANCEL:{
                result = "REQUEST_TO_SERVER_CANCEL";
                break;
            }
            case REQUEST_TO_SERVER_CATEGORY_LIST:{
                result = "REQUEST_TO_SERVER_CATEGORY_LIST";
                break;
            }
            case REQUEST_TO_SERVER_EBOOK_LIST_BY_AUTHOR:{
                result = "REQUEST_TO_SERVER_EBOOK_LIST_BY_AUTHOR";
                break;
            }
            case REQUEST_EBOOK_IMAGE_FROM_SERVER_COMPLETED:{
                result = "REQUEST_EBOOK_IMAGE_FROM_SERVER_COMPLETED";
                break;
            }
            case LOAD_MARKET_GALLERY_EBOOK:{
                result = "LOAD_MARKET_GALLERY_EBOOK";
                break;
            }
            case LOAD_MARKET_GALLERY_EBOOK_COMPLETED:{
                result = "LOAD_MARKET_GALLERY_EBOOK_COMPLETED";
                break;
            }
            case ADD_MARKET_READ_ITEM:{
                result = "ADD_MARKET_READ_ITEM";
                break;
            }
            case LOADING_GALLERY_EBOOK_IMAGE:{
                result = "LOADING_GALLERY_EBOOK_IMAGE";
                break;
            }
            case LOADING_GALLERY_EBOOK_IMAGE_COMPLETED:{
                result = "LOADING_GALLERY_EBOOK_IMAGE_COMPLETED";
                break;
            }
            case REQUEST_TO_SERVER_SUMMARY:{
                result = "REQUEST_TO_SERVER_SUMMARY";
                break;
            }
            case REQUEST_TO_SERVER_KEYWORD:{
                result = "REQUEST_TO_SERVER_KEYWORD";
                break;
            }
            case REQUEST_TO_SERVER_NOTICE_LIST:{
                result = "REQUEST_TO_SERVER_NOTICE_LIST";
                break;
            }
            case REQUEST_TO_SERVER_EBOOK_LIST_BY_CATEGORY:{
                result = "REQUEST_TO_SERVER_EBOOK_LIST_BY_CATEGORY";
                break;
            }
            case REQUEST_TO_SERVER_EBOOK_LIST_BY_PUBLISHER:{
                result = "REQUEST_TO_SERVER_EBOOK_LIST_BY_PUBLISHER";
                break;
            }
            case REQUEST_TO_SERVER_EBOOK_LIST_BY_RELEVANT:{
                result = "REQUEST_TO_SERVER_EBOOK_LIST_BY_RELEVANT";
                break;
            }
            case REQUEST_TO_SERVER_EBOOK_LIST_BY_RECOMMENDED:{
                result = "REQUEST_TO_SERVER_EBOOK_LIST_BY_RECOMMENDED";
                break;
            }
            case REQUEST_TO_SERVER_EBOOK_LIST_BY_KEYWORD:{
                result = "REQUEST_TO_SERVER_EBOOK_LIST_BY_KEYWORD";
                break;
            }
            case REQUEST_TO_SUBSCRIBE_EBOOK_FAVOURITE:{
                result = "REQUEST_TO_SUBSCRIBE_EBOOK_FAVOURITE";
                break;
            }
            case REQUEST_TO_SUBSCRIBE_EBOOK_AUTHORITIES_FAVOURITE:{
                result = "REQUEST_TO_SUBSCRIBE_EBOOK_AUTHORITIES_FAVOURITE";
                break;
            }
            case REQUEST_TO_SUBSCRIBE_EBOOK_PUBLISHER_FAVOURITE:{
                result = "REQUEST_TO_SUBSCRIBE_EBOOK_PUBLISHER_FAVOURITE";
                break;
            }
            case REQUEST_TO_SUBSCRIBE_EBOOK_RELEVANT_FAVOURITE:{
                result = "REQUEST_TO_SUBSCRIBE_EBOOK_RELEVANT_FAVOURITE";
                break;
            }
            case REQUEST_TO_SERVER_ERROR:{
                result = "REQUEST_TO_SERVER_ERROR";
                break;
            }
            case REQUEST_ERROR_REASON_INTERNAL_SERVER_ERROR:{
                result = "REQUEST_ERROR_REASON_INTERNAL_SERVER_ERROR";
                break;
            }
            case REQUEST_ERROR_REASON_UNKNOWN_HOST:{
                result = "REQUEST_ERROR_REASON_UNKNOWN_HOST";
                break;
            }
            case REQUEST_ERROR_REASON_PARAMETER_MISSING:{
                result = "REQUEST_ERROR_REASON_PARAMETER_MISSING";
                break;
            }
            case REQUEST_ERROR_REASON_HTTP_CONNECT_FAIL:{
                result= "REQUEST_ERROR_REASON_HTTP_CONNECT_FAIL";
                break;
            }
            case REQUEST_ERROR_REASON_HTTP_AUTHORIZED:{
                result = "REQUEST_ERROR_REASON_HTTP_AUTHORIZED";
                break;
            }
            case REQUEST_ERROR_REASON_UNSUPPORTED_URL:{
                result = "REQUEST_ERROR_REASON_UNSUPPORTED_URL";
                break;
            }
            case REQUEST_ERROR_REASON_JSON_PARSE_EXCEPTION:{
                result = "REQUEST_ERROR_REASON_JSON_PARSE_EXCEPTION";
                break;
            }
            case REQUEST_ERROR_REASON_IMAGE_DOWNLOAD_ERROR:{
                result = "REQUEST_ERROR_REASON_IMAGE_DOWNLOAD_ERROR";
                break;
            }
            case REQUEST_ERROR_REASON_SERVER_CONNECT_TIMEOUT:{
                result = "REQUEST_ERROR_REASON_SERVER_CONNECT_TIMEOUT";
                break;
            }
            case REQUEST_ERROR_REASON_XML_PARSE_EXCEPTION:{
                result = "REQUEST_ERROR_REASON_XML_PARSE_EXCEPTION";
                break;
            }
            case REQUEST_ERROR_REASON_SERVER_DATA_CORRUPTION:{
                result = "REQUEST_ERROR_REASON_SERVER_DATA_CORRUPTION";
                break;
            }
            case PAUSE_BACKGROUND_TASK:{
                result = "PAUSE_BACKGROUND_TASK";
                break;
            }
            case RESUME_BACKGROUND_TASK:{
                result = "RESUME_BACKGROUND_TASK";
                break;
            }
            case DECODE_EBOOK_IMAGE_BITMAP:{
                result = "DECODE_EBOOK_IMAGE_BITMAP";
                break;
            }
            case ACTION_WAITING_START:{
                result = "ACTION_WAITING_START";
                break;
            }
            case ACTION_WAITING_END:{
                result = "ACTION_WAITING_END";
                break;
            }
            case AUTO_DELETE:{
                result = "AUTO_DELETE";
                break;
            }
            case NETWORK_DISCONNECT:{
                result = "NETWORK_DISCONNECT";
                break;
            }
            case REGISTER_DEVICE_UUID_SETTINGS:{
                result = "REGISTER_DEVICE_UUID_SETTINGS";
                break;
            }
            case REGISTER_DEVICE_INFO_SETTINGS:{
                result = "REGISTER_DEVICE_INFO_SETTINGS";
                break;
            }
            default:
                break;
        }
        return result;
    }
}
