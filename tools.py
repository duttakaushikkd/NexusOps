# pyrefly: ignore [missing-import]
from langchain_core.tools import tool

@tool
def lookup_order_status(order_id: str) -> str:
    """Lookup the status of a given order by its unique ID.
    
    Args:
        order_id (str): The unique identifier of the order.
        
    Returns:
        str: A message indicating the status of the order.
    """
    # Mocking order status responses
    mock_db = {
        "12345": "Shipped. Expected delivery in 2 days.",
        "67890": "Delivered.",
        "11111": "Processing. Preparing for shipment.",
        "22222": "Cancelled."
    }
    status = mock_db.get(order_id, "Order ID not found. Please verify the ID.")
    return f"Order status for {order_id}: {status}"

@tool
def process_refund(order_id: str, reason: str) -> str:
    """Process a refund for a given order ID and reason.
    
    Args:
        order_id (str): The unique identifier of the order to refund.
        reason (str): The reason why the refund is being requested.
        
    Returns:
        str: A message indicating the status of the refund transaction.
    """
    # Mocking refund transaction responses
    if order_id in ["12345", "67890", "11111", "22222"]:
        return f"Refund successful for order {order_id}. Reason: '{reason}'. Transaction completed."
    return f"Refund failed. Order {order_id} could not be found or processed."
