package com.techiedb.app.bookman.services;

/**
 * Copyright (C) 2014 Techie Digital Benchwork Inc. All rights reserved. Mobile UX Promotion
 * Division. This software and its documentation are confidential and proprietary information of
 * Techie Digital Benchwork Inc.  No part of the software and documents may be copied, reproduced,
 * transmitted, translated, or reduced to any electronic medium or machine-readable form without the
 * prior written consent of Techie Digital Benchwork. Techie Digital Benchwork makes no
 * representations with respect to the contents, and assumes no responsibility for any errors that
 * might appear in the software and documents. This publication and the contents hereof are subject
 * to change without notice. History
 *
 * @author Larry Pham
 * @since 2014.10.08
 */
public class ResultBase {

  protected String requestOwner = null;
  protected int requestMessage = 0;
  protected int resultMessage = 0;
  protected int errorReason = 0;

  public ResultBase() {

  }

  public String getRequestOwner() {
    return requestOwner;
  }

  public void setRequestOwner(String requestOwner) {
    this.requestOwner = requestOwner;
  }

  public int getRequestMessage() {
    return requestMessage;
  }

  public void setRequestMessage(int requestMessage) {
    this.requestMessage = requestMessage;
  }

  public int getResultMessage() {
    return resultMessage;
  }

  public void setResultMessage(int resultMessage) {
    this.resultMessage = resultMessage;
  }

  public int getErrorReason() {
    return errorReason;
  }

  public void setErrorReason(int errorReason) {
    this.errorReason = errorReason;
  }
}
