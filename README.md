# London Underground

REST API for London Underground.

![alt text](https://raw.githubusercontent.com/filipecorrea/codechallenge/master/service-architecture.png "Service Architecture")

## Database API

Update database with CSV.

```
http://<server>:<port>/londonunderground/api/database/update
```

JSON response for the database update:

```javascript
{
	"success":true
}
```

## Route API

Calculate shortest route based on origin and destination stations.

```
http://<server>:<port>/londonunderground/api/route/short?from=Alperton&to=London+Bridge
```

Calculate time of shortest route based on origin and destination stations.

```
http://<server>:<port>/londonunderground/api/route/time?from=Queensway&to=Holborn
```

JSON response for the route between Queensway and Holborn:

```javascript
{
	"origin":"Queensway",
	"destination":"Holborn",
	"time":21,
	"directions":[
		"Queensway",
		"Lancaster Gate",
		"Marble Arch",
		"Bond Street",
		"Oxford Circus",
		"Tottenham Court Road",
		"Holborn"
	]
}
```