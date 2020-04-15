curl -X POST http://localhost:9000/vacation/new -H "Content-Type: application/json" -d '{
  "id": "44444444-4444-4444-4444-111111111111",
  "employeeId": "33333333-3333-3333-3333-111111111111",
  "approverId": "33333333-3333-3333-3333-111111111113",
  "period": {
    "start": "2014-01-02",
    "until": "2014-01-28"
  }
}'