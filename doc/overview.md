### Overview

The Metropolitan Atlanta Rapid Transit Authority (or "MARTA") is a public
transportation agency operating heavy rail, bus, and streetcar services for
citizens in the metro Atlanta regions of Fulton, Dekalb, and Clayton County.
MARTA's daily ridership peaks at approximately 600,000 passengers with
approximately 120,000,000 annual passengers.

MARTA provides a "real-time location" REST API for its bus and rail service.
This API provides the last known system location of every bus and train in
transit in the MARTA network.

### API In Detail

MARTA's API is relatively straightforward and simple, which serves as both a
strategic strength and practical weakness. Functionally, MARTA provides two
endpoints with similar structure and purpose - one for rail service, and another
for bus service.

Both endpoints return a complete system map with individual stop and station
schedule statuses nested within the output structure. In a concrete application,
the endpoints function best when treated as a time-series "snapshot"/"status" of
a given system. When called, the API returns known status and location of trains
and buses overlaid on the respective and complete system map. As a result,
determining individual station, stop, and bus/train status is an exercise left
to the consuming client.

### The Problem

While sufficient for short-term, surface-level insights, the API provided by
MARTA suffers from several key shortcomings that limit its potential impact to
the served communities. The fundamental problems can be grouped roughly along
the boundaries of the taxonomy listed below.

- "Ghost trains" - Often trains on the system map (identified with individual
  train IDs) will "disappear" between stations, only returning when during
  arrival and embarking.

- Data incongruities - Nodes for specific trains and stations list arrival time
  forecasts as part of their individual insights, but these insights are often
  wildly inaccurate and "jumpy". A train might be listed as 6 minutes away only
  to jump up to 20 minutes before returning to 5 minutes during the next cycle
  of display updates. Such incongruities lead to frequent guesswork for users
  who are unsure if they've just missed their train or if they're on time.

- Data absences - Occasionally, the API will fail to return any system data for
  a given station or train - it's simply absent in the returned API envelope.
  When you see a blank screen or "No Data" in the station radiators, this is the
  culprit.

- Mismarked trains - Some trains are incorrectly labeled on the tracks and in
  the real-time system. The semi-rare sightings of express and out-of-service
  ("NO PASSENGERS") trains might be mislabeled or unreported.

- Adapted results - As with any system at scale, MARTA's schedules must adapt
  with circumstances. Currently, MARTA's API does _not_ adjust API results for
  daily incidents with any delays, closures, or even routine maintenance
  schedule adjustments announced via the official MARTA twitter.

Examples:

![alt text](https://i.imgur.com/xD57K9S.jpg "No data")

![alt text](https://i.imgur.com/9KLhrgn.png "Twitter")

![alt text](https://i.imgur.com/3yWBJ1v.jpg "Ghost train")

![alt text](https://i.imgur.com/9IgWE7G.jpg "Incongruent data")

### The Goal

With these problems in mind, my goal for this project is to consolidate and
codify my habits and workflows as a daily MARTA user into a single client that
can act as a single source of truth for users. I see this as a fun and
challenging exercise, but also one of nominal ethical obligation. MARTA serves
as the only means of transportation for many of the poorest and under-served
communities in the region, and MARTA's capacity to remedy these shortcomings is
prone to the political whims of Gold Dome sessions. I want this client to serve
as the foundational basis for a scalable, insightful portal for individual users
to gain reliable insights into their daily commutes.

From a practical standpoint, that means triangulating the various sources of
truth in the MARTA system - static schedules, API results and Twitter
announcements. This data can be paired with individual arrival snapshots to
create a rudimentary model for predictive insights. In plain terms, if we can
collect enough arrival/departure insights, we can start picking up on trends and
forecasting results for users.

A practical use-case scenario might be one where a user requests station
arrivals around rush hour, and in addition to the real-time and static arrival
data, a warning/notification is included noting something like _"Red line trains
from 5 Point station frequently delayed 5+ minutes at this time."_
