'use strict';
/*
 * Copyright (c) 2016 TopCoder, Inc. All rights reserved.
 */
/**
 * Contains express application configurations.
 */
if (!process.env.NODE_ENV) {
  process.env.NODE_ENV = 'development';
}

const cors = require('cors');
const config = require('./config');
const express = require('express');
const app = express();
const morgan = require('morgan');
const bodyParser = require('body-parser');
const passport = require('passport');
const logger = require('./common/logger');
const _ = require('lodash');
const mongoose = require('mongoose');
mongoose.Promise = global.Promise;

mongoose.connect(config.MONGODB_URI);

// Assign a unique request id
const assignReqId = (() => {
  let index = 0;
  return () => index++;
})();

app.use((req, res, next) => {
  req.id = assignReqId();
  next();
});

app.use(cors());
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));
app.use(morgan('dev'));

require('./app-passport')(app);
require('./app-routes')(app);
app.use(passport.initialize());


// The error handler,log error and return 500 error
// eslint-disable-next-line no-unused-vars
app.use((err, req, res, next) => {
  logger.logFullError(err, req.signature || `${req.method} ${req.url}`);
  const errorResponse = {
    id: req.id,
    version: config.VERSION,
    result: {
      success: false,
      status: (err.isJoi) ? 400 : err.httpStatus || 500,
      debug: err,
      content: {}
    }
  };

  if (_.isArray(err.details)) {
    errorResponse.result.content.fields = _.map(err.details, 'path').join(', ');
    if (err.isJoi) {
      _.map(err.details, (e) => {
        if (e.message) {
          if (_.isUndefined(errorResponse.result.content.message)) {
            errorResponse.result.content.message = e.message;
          } else {
            errorResponse.result.content.message += ', ' + e.message;
          }
        }
      });
    }
  }
  if (_.isUndefined(errorResponse.result.content.message)) {
    if (err.message) {
      errorResponse.result.content.message = err.message;
    } else {
      errorResponse.result.content.message = 'server error';
    }
  }

  res.status(errorResponse.result.status).json(errorResponse);
});

const port = config.PORT;
app.listen(port, '0.0.0.0');
logger.info('Server listening on port %d in %s mode', port, process.env.NODE_ENV);
