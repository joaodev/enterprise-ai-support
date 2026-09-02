export interface AssistCustomerRequest {
  customerId: string;
  orderId: string;
  message: string;
}

export interface AssistCustomerResult {
  answer: string;
  orderId: string;
  orderStatus: OrderStatus;
  ticketId: string | null;
  executedTools: string[];
}

export type OrderStatus =
  | 'PROCESSING'
  | 'SHIPPED'
  | 'DELIVERED'
  | 'CANCELLED';

