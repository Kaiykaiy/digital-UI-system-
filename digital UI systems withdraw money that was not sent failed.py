def process_withdrawal_response(xml_response):
    try:
        # Parse XML response
        status = xml_response.find("status").text
        if status == "failure":
            error_code = xml_response.find("error/code").text
            error_message = xml_response.find("error/message").text
            print(f"Withdrawal failed with error {error_code}: {error_message}")
        else:
            transaction_id = xml_response.find("transactionId").text
            amount = xml_response.find("amount").text
            print(f"Withdrawal successful! Transaction ID: {transaction_id}, Amount: {amount}")
    except Exception as e:
        print(f"Error processing response: {str(e)}")
