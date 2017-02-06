'use strict';
/*
 * Copyright (c) 2016 TopCoder, Inc. All rights reserved.
 */

/**
 * This controller exposes User related actions.
 */
const UserService = require('../services/UserService');
const config = require('../../../config');

/**
 * Create new User
 * @param req the request
 * @param res the response
 * @param next the next middleware
 */
function* create(req, res, next) {
  yield UserService.createUser(req.body, (err, newUserId) => {
    if (err) return next(err);
    res.json({
      id: req.id,
      version: config.VERSION,
      result: {
        success: true,
        status: 200,
        content: {
          newUserId
        }
      }
    });
  });
}


/**
 * Update an existing User.
 * @param req the request
 * @param res the response
 * @param next the next middleware
 */
function* updateOne(req, res, next) {
  yield UserService.updateOne(req.params.userId, req.body, (err, updatedUser) => {
    if (err) return next(err);
    res.json({
      id: req.id,
      version: config.VERSION,
      result: {
        success: true,
        status: 200,
        content: {
          updatedUser
        }
      }
    });
  });
}

module.exports = {
  create,
  updateOne
};
