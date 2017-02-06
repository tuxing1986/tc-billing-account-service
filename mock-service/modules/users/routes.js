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
  '/users': {
    post: {
      controller: 'UserController',
      method: 'create'
    }
  },
  '/users/:userId': {
    patch: {
      auth: jwtAuth,
      access: [UserRole.ADMIN],
      controller: 'UserController',
      method: 'updateOne'
    }
  }
};
