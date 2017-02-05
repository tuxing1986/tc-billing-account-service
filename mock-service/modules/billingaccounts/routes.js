'use strict';
/*
 * Copyright (c) 2016 TopCoder, Inc. All rights reserved.
 */

/**
 * Contains all routes.
 */

const constants = require('../../constants');
const UserRole = constants.UserRole;
const jwtAuth = constants.Passports.jwt;

module.exports = {
  '/billingaccounts': {
    get: {
      auth: jwtAuth,
      access: [UserRole.ADMIN],
      controller: 'BillingAccountsController',
      method: 'advancedSearch'
    },
    post: {
      auth: jwtAuth,
      access: [UserRole.ADMIN],
      controller: 'BillingAccountsController',
      method: 'create'
    }
  },
  '/billingaccounts/:billingAccountId': {
    get: {
      auth: jwtAuth,
      access: [UserRole.ADMIN],
      controller: 'BillingAccountsController',
      method: 'getOne'
    },
    patch: {
      auth: jwtAuth,
      access: [UserRole.ADMIN],
      controller: 'BillingAccountsController',
      method: 'updateOne'
    }
  },
  '/billingaccounts/:billingAccountId/users': {
    get: {
      auth: jwtAuth,
      access: [UserRole.ADMIN],
      controller: 'BillingAccountsController',
      method: 'getAccountUsers'
    },
    post: {
      auth: jwtAuth,
      access: [UserRole.ADMIN],
      controller: 'BillingAccountsController',
      method: 'assignUserToAccount'
    }
  },
  '/billingaccounts/:billingAccountId/users/:userId': {
    delete: {
      auth: jwtAuth,
      access: [UserRole.ADMIN],
      controller: 'BillingAccountsController',
      method: 'deleteUserFromAccount'
    }
  },
  '/me/billingaccounts': {
    get: {
      auth: jwtAuth,
      access: [UserRole.ADMIN, UserRole.USER],
      controller: 'BillingAccountsController',
      method: 'search'
    }
  }
};
