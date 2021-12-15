awslocal configure set aws_access_key_id test
awslocal configure set aws_secret_access_key test
awslocal configure set region us-east-1

awslocal sqs create-queue --queue-name test-queue
awslocal sns create-topic --name test-topic
