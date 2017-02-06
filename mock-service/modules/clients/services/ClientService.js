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
const Client = require('../../../models/Client');
const _ = require('lodash');

/**
 * Find all Clients
 * @param {Function} cb the callback function
 */
function* findAll(cb) {
  Client.find({}, (err, clientList) => {
    if (err) return cb(err);
    const clients = [];
    _.each(clientList, (client) => {
      client = client.toObject();
      client.id = client._id;
      delete client._id;
      delete client.__v;
      clients.push(client);
    });
    cb(err, clients);
  });
}

findAll.schema = {
  cb: Joi.func().required()
};

/**
 * Create new Client
 * @param {Object} client the new client
 * @param {Function} cb the callback function
 */
function* createClient(client, cb) {
  Client.create(client, (err, newClient) => {
    if (err) return cb(err);
    cb(err, newClient._id);
  });
}

createClient.schema = {
  client: Joi.object({
    name: Joi.string().required(),
    status: Joi.string().required(),
    startDate: Joi.string().required(),
    endDate: Joi.string().required(),
    codeName: Joi.string().required()
  }),
  cb: Joi.func().required()
};

/**
 * Update an existing Client.
 * @param {String} clientId the client ID
 * @param {Object} updatedClient the updated client object
 * @param {Function} cb the callback function
 */
function* updateOne(clientId, updatedClient, cb) {
  Client.findByIdAndUpdate(clientId, updatedClient, (err, updated) => {
    if (!updated) err = new NotFoundError(`Client with id: ${clientId} not found!`);
    if (updated) {
      updated = updated.toObject();
      updated.id = updated._id;
      delete updated._id;
      delete updated.__v;
    }
    cb(err, updated);
  });
}

updateOne.schema = {
  clientId: Joi.string(),
  updatedClient: Joi.object({
    name: Joi.string().required(),
    status: Joi.string().required(),
    startDate: Joi.string().required(),
    endDate: Joi.string().required(),
    codeName: Joi.string().required()
  }),
  cb: Joi.func().required()
};

/**
 * Update an existing Client.
 * @param {String} clientId the client ID
 * @param {Function} cb the callback function
 */
function* getOne(clientId, cb) {
  Client.find({ _id: clientId}, (err, client) => {
    if (!client || !client[0]) err = new NotFoundError(`Client with id: ${clientId} not found!`);
    if (client && client[0]) {
      client = client[0].toObject();
      client.id = client._id;
      delete client._id;
      delete client.__v;
    }
    cb(err, client);
  });
}

getOne.schema = {
  clientId: Joi.string(),
  cb: Joi.func().required()
};

module.exports = {
  findAll,
  createClient,
  updateOne,
  getOne
};

logger.buildService(module.exports);
