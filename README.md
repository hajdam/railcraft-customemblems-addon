Custom Emblems Addon for Railcraft Mod
======================================

Support for custom emblems for decorative purposes.

You can put emblems into frames, on railcraft locomotives or combine with metal posts of various colors to form simple signs.

Screenshot
----------

![Screenshot](images/example_screenshot.jpg?raw=true)

Recipes
-------

Custom Engraving Bench

![Custom Engraving Bench Recipe](images/custom_engraving_bench_recipe.png?raw=true)

Metal Post sign with plate

![Metal Post Plate Recipe](images/metal_post_plate_recipe.png?raw=true)

Metal Post sign with no plate

![Metal Post Sign Recipe](images/metal_post_sign_recipe.png?raw=true)

Usage
-----

You can make your own emblems by editing mod file. Jar mod file is actually zip - you can rename it for editing if your archive program doesn't recognize it.

Navigate to: assets/railcraft_cemblem/textures/emblems/

Directory contains texture images used for custom emblems. You can add your own.

custom.properties is a text file containing list of custom emblems with unique code, name to display and image to use.

When emblem code is starting with "." (dot) it's not available for engraving and can be only created with /give command so it could be for example given to players from admins for events or so... 

Example give: /give player railcraft_cemblem:cemblem.custom_emblem 1 0 {emblem:cust_exit}

Building
--------

./gradlew build

