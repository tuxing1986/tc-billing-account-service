'use strict';
/*
 * Copyright (c) 2016 TopCoder, Inc. All rights reserved.
 */

/**
 * This controller exposes Client related actions.
 */
const ClientService = require('../services/ClientService');
const config = require('../../../config');

/**
 * Find Clients
 * @param req the request
 * @param res the response
 * @param next the next middleware
 */
function* findAll(req, res, next) {
  yield ClientService.findAll((err, clients) => {
    if (err) return next(err);
    res.json({
      id: req.id,
      version: config.VERSION,
      result: {
        success: true,
        status: 200,
        content: {
          clients
        }
      }
    });
  });
}

/**
 * Create new Client
 * @param req the request
 * @param res the response
 * @param next the next middleware
 */
function* create(req, res, next) {
  yield ClientService.createClient(req.body, (err, newClientId) => {
    if (err) return next(err);
    res.json({
      id: req.id,
      version: config.VERSION,
      result: {
        success: true,
        status: 200,
        content: {
          newClientId
        }
      }
    });
  });
}


/**
 * Update an existing Client.
 * @param req the request
 * @param res the response
 * @param next the next middleware
 */
function* updateOne(req, res, next) {
  yield ClientService.updateOne(req.params.clientId, req.body, (err, updatedClient) => {
    if (err) return next(err);
    res.json({
      id: req.id,
      version: config.VERSION,
      result: {
        success: true,
        status: 200,
        content: {
          updatedClient
        }
      }
    });
  });
}

/**
 * Get an existing Client.
 * @param req the request
 * @param res the response
 * @param next the next middleware
 */
function* getOne(req, res, next) {
  yield ClientService.getOne(req.params.clientId, (err, client) => {
    if (err) return next(err);
    res.json({
      id: req.id,
      version: config.VERSION,
      result: {
        success: true,
        status: 200,
        content: {
          client
        }
      }
    });
  });
}

module.exports = {
  findAll,
  create,
  updateOne,
  getOne
};
