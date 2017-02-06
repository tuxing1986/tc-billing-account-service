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
  '/clients': {
    get: {
      auth: jwtAuth,
      access: [UserRole.ADMIN],
      controller: 'ClientController',
      method: 'findAll'
    },
    post: {
      auth: jwtAuth,
      access: [UserRole.ADMIN],
      controller: 'ClientController',
      method: 'create'
    }
  },
  '/clients/:clientId': {
    get: {
      auth: jwtAuth,
      access: [UserRole.ADMIN],
      controller: 'ClientController',
      method: 'getOne'
    },
    patch: {
      auth: jwtAuth,
      access: [UserRole.ADMIN],
      controller: 'ClientController',
      method: 'updateOne'
    }
  }
};
