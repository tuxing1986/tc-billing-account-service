'use strict';
/*
 * Copyright (c) 2016 TopCoder, Inc. All rights reserved.
 */

/**
 * This service will provide all operations with the database.
 */
const _ = require('lodash');
const Joi = require('joi');
const NotFoundError = require('../../../common/errors').NotFoundError;
const BadRequestError = require('../../../common/errors').BadRequestError;
const logger = require('../../../common/logger');
const BillingAccount = require('../../../models/BillingAccount');

/**
 * Create a billing account
 * @param {Object} account the account object
 * @param {String} userId the user id
 * @param {Function} cb the callback function
 */
function* create(account, userId, cb) {
  account.startDate = new Date(account.startDate);
  account.endDate = new Date(account.endDate);
  account.createdBy = userId;
  BillingAccount.create(account, (err, newAccount) => {
    if (newAccount) {
      newAccount = newAccount.toObject();
      newAccount.id = newAccount._id;
      delete newAccount.users;
      delete newAccount._id;
      delete newAccount.__v;
    }
    cb(err, newAccount);
  });
}

create.schema = {
  account: Joi.object().keys({
    customerNumber: Joi.string().required(),
    name: Joi.string().required(),
    status: Joi.string().required(),
    startDate: Joi.string().required(),
    endDate: Joi.string().required()
  }),
  userId: Joi.number().required(),
  cb: Joi.func().required()
};


/**
 * Search
 * @param {Object} query the search criteria
 * @param {Function} cb the callback function
 */
function* search(query, cb) {
  const criteria = _.pick(query, ['status']);
  const customSort = {};
  customSort[query.sort || 'id'] = -1;
  if (query.startDate) {
    criteria.startDate = {
      $gte: new Date(query.startDate)
    };
  }
  if (query.endDate) {
    criteria.endDate = {
      $lte: new Date(query.endDate)
    };
  }
  BillingAccount
  .find(criteria)
  .sort(customSort)
  .select('_id customerNumber name status startDate endDate budgetAmount description poNumber paymentTerms subscriptionNumber createdAt createdBy updatedAt updatedBy')
  .skip(query.offset ? query.offset * (query.limit || 10) : 0)
  .limit(query.limit || 10)
  .exec((err, accounts) => {
    const result = [];
    if (accounts) {
      _.each(accounts, (account) => {
        account = account.toObject();
        account.id = account._id;
        delete account.__v;
        delete account._id;
        delete account.users;
        result.push(account);
      });
    }
    cb(err, result);
  });
}

search.schema = {
  query: Joi.object().keys({
    offset: Joi.number().min(0),
    limit: Joi.number().min(0),
    status: Joi.string().required(),
    startDate: Joi.string().required(),
    endDate: Joi.string().required(),
    sort: Joi.string()
  }),
  cb: Joi.func().required()
};

/**
 * Advance search
 * @param {Object} query the search criteria
 * @param {Function} cb the callback function
 */
function* advancedSearch(query, cb) {
  const criteria = _.pick(query, ['status', 'customer']);
  if (query.startDate) {
    criteria.startDate = {
      $gte: new Date(query.startDate)
    };
  }
  if (query.endDate) {
    criteria.endDate = {
      $lte: new Date(query.endDate)
    };
  }
  if (query.user) {
    criteria['users.name'] = query.user;
  }
  const customSort = {};
  customSort[query.sort || 'id'] = -1;
  BillingAccount
  .find(criteria)
  .populate('BillingAccountUser')
  .sort(customSort)
  .select('_id customerNumber name status startDate endDate budgetAmount description poNumber paymentTerms subscriptionNumber createdAt createdBy updatedAt updatedBy')
  .skip(query.offset ? query.offset * (query.limit || 10) : 0)
  .limit(query.limit || 10)
  .exec((err, accounts) => {
    const result = [];
    if (accounts) {
      _.each(accounts, (account) => {
        account = account.toObject();
        account.id = account._id;
        delete account.__v;
        delete account.users;
        delete account._id;
        result.push(account);
      });
    }
    cb(err, result);
  });
}

advancedSearch.schema = {
  query: Joi.object().keys({
    offset: Joi.number().min(0),
    limit: Joi.number().min(0),
    status: Joi.string().required(),
    customer: Joi.number().required(),
    user: Joi.string().required(),
    startDate: Joi.string().required(),
    endDate: Joi.string().required(),
    sort: Joi.string()
  }),
  cb: Joi.func().required()
};

/**
 * Get an object by object type and ID.
 * @param {String} accountId the account id
 * @param {Function} cb the callback function
 */
function* getOne(accountId, cb) {
  BillingAccount.findById(accountId, (err, account) => {
    if (account) {
      account = account.toObject();
      account.id = account._id;
      delete account._id;
      delete account.__v;
      delete account.users;
    }
    cb(err, account);
  });
}

getOne.schema = {
  accountId: Joi.string().required(),
  cb: Joi.func()
};

/**
 * Update an object.
 * @param {String} accountId the account id
 * @param {userId} userId the user id
 * @param {Object} updatedFields the updated fields
 * @param {Fucntion} cb the callback function
 */
function* updateOne(accountId, userId, updatedFields, cb) {
  updatedFields.updatedAt = Date.now();
  updatedFields.updatedBy = userId;
  BillingAccount.findByIdAndUpdate(accountId, updatedFields, (err, account) => {
    if (account) {
      account = account.toObject();
      account.id = account._id;
      delete account._id;
      delete account.__v;
      delete account.users;
      account = _.merge(account, updatedFields);
    }
    cb(err, account);
  });
}

updateOne.schema = {
  accountId: Joi.string(),
  userId: Joi.number().required(),
  updatedFields: Joi.object().keys({
    customerNumber: Joi.string(),
    name: Joi.string(),
    status: Joi.string(),
    startDate: Joi.string(),
    endDate: Joi.string(),
    budgetAmount: Joi.number(),
    description: Joi.string(),
    poNumber: Joi.number(),
    paymentTerms: Joi.string(),
    subscriptionNumber: Joi.number()
  }),
  cb: Joi.func().required()
};

/**
 * Get users from billing account
 * @param {String} accountId the account id
 * @param {Function} cb the callback function
 */
function* getAccountUsers(accountId, cb) {
  BillingAccount.find({ _id: accountId }).populate('users', '_id name description').exec((err, account) => {
    if (!account || account.length === 0) return cb(new NotFoundError(`Billing Account with id: ${accountId} not found!`));
    account = account[0].toObject();
    const users = account.users;
    _.each(users, (user) => {
      user.id = user._id;
      delete user._id;
    });
    cb(err, users);
  });
}

getAccountUsers.schema = {
  accountId: Joi.string(),
  cb: Joi.func()
};

/**
 * Assign user to a billing account
 * @param {String} accountId the account id
 * @param {String} userId the user id
 * @param {Function} cb the callback function
 */
function* assignUserToAccount(accountId, userId, cb) {
  BillingAccount.findById(accountId, (err, account) => {
    if (!account) return cb(new NotFoundError(`Billing Account with id: ${accountId} not found!`));
    const existing = _.find(account.toObject().users, (u) => u.toString() === userId);
    if (existing) return cb(new BadRequestError(`User with id: ${userId} already exist in account with id: ${accountId}`));
    if (!account.users) account.users = [];
    account.users.push(userId);
    account.save((saveErr) => {
      if (account) {
        account = account.toObject();
        account.id = account._id;
        delete account._id;
        delete account.__v;
        delete account.users;
      }
      cb(err || saveErr, account);
    });
  });
}

assignUserToAccount.schema = {
  accountId: Joi.string().required(),
  userId: Joi.string().required(),
  cb: Joi.func()
};

/**
 * Delete user from a billing account
 * @param {String} accountId the account id
 * @param {String} userId the user id
 * @param {Function} cb the callback function
 */
function* deleteUserFromAccount(accountId, userId, cb) {
  BillingAccount.findByIdAndUpdate(accountId, { $pull: { users: userId } }, (err, account) => {
    if (!account) return cb(new NotFoundError(`Billing Account with id: ${accountId} not found!`));
    cb(err);
  });
}

assignUserToAccount.schema = {
  accountId: Joi.string().required(),
  userId: Joi.string().required(),
  cb: Joi.func()
};


module.exports = {
  create,
  search,
  getOne,
  updateOne,
  advancedSearch,
  getAccountUsers,
  assignUserToAccount,
  deleteUserFromAccount
};

logger.buildService(module.exports);
