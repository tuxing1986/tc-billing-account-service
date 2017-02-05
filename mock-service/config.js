'use strict';
/*
 * Copyright (c) 2016 TopCoder, Inc. All rights reserved.
 */

/**
 * Application configuration
 */

module.exports = {
  PORT: process.env.PORT || 3000,
  MONGODB_URI: process.env.MONGODB_URI || 'mongodb://localhost:27017/billing-account',
  AUTH_SECRET: 'secret',
  LOG_LEVEL: 'debug',
  VERSION: 'v1'
};
