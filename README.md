# issue 3700
### Summary
- StorageTask(`getFile`) cannot be canceled(`task.cancel`) while the network connection is unavailable.
### Prerequisite
- add `google-services.json`
### Steps to reproduce
1. Upload a photo in FirebaseStorage dashboard
2. Turn off mobile data and wifi
3. Click `retrieve file from storage` button, 
4. See logs that the StorageTask will continually persists until network is available (`W/ExponenentialBackoff: network unavailable, sleeping.`)
