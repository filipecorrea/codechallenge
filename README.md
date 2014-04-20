# London Underground

API for London Underground.

## Database API

Update database with CSV.

```
http://<server>:<port>/londonunderground/api/database/update
```

## Route API

Calculate shortest route based on origin and destination stations.

```
http://<server>:<port>/londonunderground/api/route/short?from=Alperton&to=London+Bridge
```

Calculate time of shortest route considering 3 minutes for traveling between stations on the same line and 12 minutes for changing lines.

```
http://<server>:<port>/londonunderground/api/route/time?from=Queensway&to=Holborn
```

This is the JSON response for the route between Queensway and Holborn:

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