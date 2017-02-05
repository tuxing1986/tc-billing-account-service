'use strict';
/*
 * Copyright (c) 2016 TopCoder, Inc. All rights reserved.
 */

/**
 * This service will provide all operations with the database.
 */
const Joi = require('joi');
const NotFoundError = require('../../../common/errors').NotFoundError;
const logger = require('../../../common/logger');
const User = require('../../../models/BillingAccountUser');

/**
 * Create new User
 * @param {Object} user the new user
 * @param {Function} cb the callback function
 */
function* createUser(user, cb) {
  User.create(user, (err, newUser) => {
    cb(err, newUser._id);
  });
}

createUser.schema = {
  user: Joi.object({
    name: Joi.string().required(),
    description: Joi.string(),
    role: Joi.string().valid(['admin', 'user'])
  }),
  cb: Joi.func().required()
};

/**
 * Update an existing User.
 * @param {String} userId the user ID
 * @param {Object} updatedUser the updated user object
 * @param {Function} cb the callback function
 */
function* updateOne(userId, updatedUser, cb) {
  User.findByIdAndUpdate(userId, updatedUser, (err, updated) => {
    if (!updated) err = new NotFoundError(`User with id: ${userId} not found!`);
    if (updated) {
      updated = updated.toObject();
      updated.id = updated._id;
      delete updated._id;
    }
    cb(err, updated);
  });
}

updateOne.schema = {
  userId: Joi.string(),
  updatedUser: Joi.object({
    name: Joi.string().required(),
    description: Joi.string(),
    role: Joi.string().valid(['admin', 'user'])
  }),
  cb: Joi.func().required()
};

module.exports = {
  createUser,
  updateOne
};

logger.buildService(module.exports);
