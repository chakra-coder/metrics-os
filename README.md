# Java project template

This repository contains template for my new Java projects. Although this isn't very ground breaking, 
it saves me time in hunting down my newest code formatting profile and clicking through all the menus 
to set up the whole thing.

Please note that some of these settings are just my preferences. 


## Quick start 
First do:

    $ mkdir -p ${MY_AWESOME_PROJECT}
    $ cd ${MY_AWESOME_PROJECT}
    $ git clone https://github.com/tguzik/java-project-template
    $ rm -rf .git
    $ git init 
    $ git add -f .
    $ git commit -m "Initial commit"
    $ git remote add --track master origin https://github.com/${USERNAME}/${MY_AWESOME_PROJECT}.git


## Things included

* `.idea` - code formatting settings for IntelliJ IDEA
* `pom.xml` - template Maven profile with few very common dependencies (*commons-lang3*, 
   *guava*, *junit*, *mockito*)
* `LICENSE` - MIT license
* `.gitignore` - ignores for generated files and directories
