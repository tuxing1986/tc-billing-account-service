'use strict';
/*
 * Copyright (c) 2016 TopCoder, Inc. All rights reserved.
 */

/*
 * Client model
 */

const mongoose = require('mongoose');

const Schema = mongoose.Schema;

const ClientSchema = new Schema({
  name: { type: String, required: true },
  status: { type: String, required: true },
  startDate: { type: Date, required: true },
  endDate: { type: Date, required: true },
  codeName: {type: String, required: true }
});

module.exports = mongoose.model('Client', ClientSchema);
