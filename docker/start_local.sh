docker compose -p ticketmaster up -d

echo "Waiting for 15 seconds..."
sleep 15
echo "Done waiting."

aws --endpoint-url=http://localhost:4566 sqs create-queue \
    --queue-name check-booking-pending-state
