import { Deposit } from "core/domain/deposit";
import mongoose, { Schema, Document } from "mongoose";

// Define the Mongoose interface extending the Mongoose Document
export interface Deposit extends Document, Deposit {}

// Define the Mongoose schema based on the Zod schema
const DepositSchema: Schema = new Schema({
  blockNumber: { type: String, required: true },
  blockTimestamp: { type: String, required: true },
  fee: { type: String, required: false },
  hash: { type: String, required: false },
  pubkey: { type: String, required: true },
  blockchain: { type: String, required: true },
  network: { type: String, required: true },
  token: { type: String, required: true },
});

// Create and export the Mongoose model
export const DepositModel = mongoose.model<Deposit>("Deposit", DepositSchema);