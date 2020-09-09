#Riby Android Test

Synopsis
The objective of this test is to create an app that tracks the movement of a device. As the device moves from point A to point B, capture the coordinate and save the coordinate in the database.

Implementation
User clicks on the start button to initiate the capturing effect. The app displays a stop button to stop the capturing process when the user clicks stop The app captures the devices coordinate using the GPS hardware The app stores the coordinate in the DB using Google's Room API Using the saved coordinate, the app show the distance covered using the Google's map API

Requirement
You must implement the database using Google's Room API and not direct sqlite. The capturing phase of the app should work even when the device is offline.

Duration
The time for this test is 24hours
