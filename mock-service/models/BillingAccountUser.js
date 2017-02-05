'use strict';
/*
 * Copyright (c) 2016 TopCoder, Inc. All rights reserved.
 */

/*
 * Billing Account User model
 */

const mongoose = require('mongoose');

const Schema = mongoose.Schema;
const USER_ROLES = ['admin', 'user'];

const billingAccountUserSchema = new Schema({
  name: { type: String, required: true },
  description: String,
  role: { type: String, enum: USER_ROLES, require: true, default: 'user' }
});

module.exports = mongoose.model('BillingAccountUser', billingAccountUserSchema);
