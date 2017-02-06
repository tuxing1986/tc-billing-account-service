'use strict';
/*
 * Copyright (c) 2016 TopCoder, Inc. All rights reserved.
 */

/**
 * This controller exposes BillingAccount related actions.
 */
const BillingAccountService = require('../services/BillingAccountsService');
const NotFoundError = require('../../../common/errors').NotFoundError;
const BadRequestError = require('../../../common/errors').BadRequestError;
const config = require('../../../config');

/**
 * Create an object
 * @param req the request
 * @param res the response
 * @param next the next middleware
 */
function* create(req, res, next) {
  if (!req.body.param) return next(new BadRequestError('Invalid data!'));
  yield BillingAccountService.create(req.body.param, req.user.id, (err, account) => {
    if (err) return next(err);
    res.status(201).json({
      id: req.id,
      version: config.VERSION,
      result: {
        success: true,
        status: 201,
        content: account
      }
    });
  });
}

/**
 * Search Objects
 * @param req the request
 * @param res the response
 * @param next the next middleware
 */
function* search(req, res, next) {
  yield BillingAccountService.search(req.query, (err, accounts) => {
    if (err) return next(err);
    res.json({
      id: req.id,
      version: config.VERSION,
      result: {
        success: true,
        status: 200,
        metadata: {
          totalCount: accounts.length
        },
        content: accounts
      }
    });
  });
}

/**
 * Advance search
 * @param req the request
 * @param res the response
 * @param next the next middleware
 */
function* advancedSearch(req, res, next) {
  yield BillingAccountService.advancedSearch(req.query, (err, accounts) => {
    if (err) return next(err);
    res.json({
      id: req.id,
      version: config.VERSION,
      result: {
        success: true,
        status: 200,
        metadata: {
          totalCount: accounts.length
        },
        content: accounts
      }
    });
  });
}


/**
 * Update an object.
 * @param req the request
 * @param res the response
 * @param next the next middleware
 */
function* updateOne(req, res, next) {
  if (!req.body.param) return next(new BadRequestError('Invalid data!'));
  yield BillingAccountService.updateOne(req.params.billingAccountId, req.user.id, req.body.param, (err, account) => {
    if (err) return next(err);
    if (!account) return next(new NotFoundError(`Billing Account with id: ${req.params.billingAccountId} not found!`));
    res.json({
      id: req.id,
      version: config.VERSION,
      result: {
        success: true,
        status: 200,
        content: account
      }
    });
  });
}


/**
 * Get a single billing account
 * @param req the request
 * @param res the response
 * @param next the next middleware
 */
function* getOne(req, res, next) {
  yield BillingAccountService.getOne(req.params.billingAccountId, (err, account) => {
    if (err) return next(err);
    if (!account) return next(new NotFoundError(`Billing Account with id: ${req.params.billingAccountId} not found!`));
    res.json({
      id: req.id,
      version: config.VERSION,
      result: {
        success: true,
        status: 200,
        content: account
      }
    });
  });
}

/**
 * Get users from a billing account
 * @param req the request
 * @param res the response
 * @param next the next middleware
 */
function* getAccountUsers(req, res, next) {
  yield BillingAccountService.getAccountUsers(req.params.billingAccountId, (err, users) => {
    if (err) return next(err);
    res.json({
      id: req.id,
      version: config.VERSION,
      result: {
        success: true,
        status: 200,
        metadata: {
          totalCount: users.length
        },
        content: users
      }
    });
  });
}

/**
 * Assign user to billing account
 * @param req the request
 * @param res the response
 * @param next the next middleware
 */
function* assignUserToAccount(req, res, next) {
  yield BillingAccountService.assignUserToAccount(req.params.billingAccountId, req.body.userId, (err, account) => {
    if (err) return next(err);
    res.json({
      id: req.id,
      version: config.VERSION,
      result: {
        success: true,
        status: 200,
        content: account
      }
    });
  });
}

/**
 * Delete user from billing account
 * @param req the request
 * @param res the response
 * @param next the next middleware
 */
function* deleteUserFromAccount(req, res, next) {
  yield BillingAccountService.deleteUserFromAccount(req.params.billingAccountId, req.params.userId, (err) => {
    if (err) return next(err);
    res.sendStatus(204);
  });
}

module.exports = {
  create,
  search,
  advancedSearch,
  updateOne,
  getOne,
  getAccountUsers,
  assignUserToAccount,
  deleteUserFromAccount
};
