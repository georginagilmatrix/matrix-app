# matrix-app
Repository for Automation Framework for APP Matrix

Execute Framework from command line

To execute the tests from command line, execute the following command:

For Real Device

clean cucumber --info -PtagSelected=@SuccessfulSignUp -Denv=dev -DplatformName=android -Dapp=app-dev-prod-release.apk -DrunWith=device -DdeviceName=K22+ -Dudid=6009cadc -DplatformVersion=10.0

For Emulators

clean cucumber --info -PtagSelected=@SuccessfulSignUp -Denv=dev -DplatformName=android -Dapp=app-dev-prod-release.apk -DrunWith=emulator -Davd=Pixel_3a_API_30

