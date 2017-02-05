'use strict';
/*
 * Copyright (c) 2016 TopCoder, Inc. All rights reserved.
 */

/*
 * Billing Account model
 */

const mongoose = require('mongoose');

const Schema = mongoose.Schema;

const billingAccountSchema = new Schema({
  customerNumber: { type: String, required: true },
  name: { type: String, required: true },
  status: { type: String, required: true },
  startDate: { type: Date, required: true },
  endDate: { type: Date, required: true },
  users: [{ type: Schema.Types.ObjectId, ref: 'BillingAccountUser' }],
  amount: Number,
  createdAt: { type: Date, required: true, default: Date.now() },
  createdBy: { type: Number, required: true },
  updatedAt: Date,
  updatedBy: Number
});

module.exports = mongoose.model('BillingAccount', billingAccountSchema);
