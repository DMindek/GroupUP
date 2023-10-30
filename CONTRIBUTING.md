# Contributing to the project
To contribute to this project you must adhere to the following guidelines.
## Disclaimer
All images in this document are the property of their respective owners and have been taken from the following link:
<br> https://www.atlassian.com/git/tutorials/comparing-workflows/gitflow-workflow <br>
For detailed descriptions of the model visit the above link.

## Branching model
The branching model of this project follows the GitFlow model. The main premise is that there are seperate branches for the realease versions of the appliaction and the versions that are in development. 
Following this are some main points that need to be adhered to.<br>
### Development and main branches
![Gitflow model 1](https://wac-cdn.atlassian.com/dam/jcr:a13c18d6-94f3-4fc4-84fb-2b8f1b2fd339/01%20How%20it%20works.svg?cdnVersion=1286)

<br>The "main" branch should be used for commiting release versions of the software that must be fully tested. 
The the "development" branch is used for managing the integration of functionalities into the main branch. 
Direct commits to main are not allowed. <br>
<b>The code in this branch should always be compileable and the code being merged should always be done via pull request.</b>

### Feature branches
![Gitflow model 2](https://wac-cdn.atlassian.com/dam/jcr:34c86360-8dea-4be4-92f7-6597d4d5bfae/02%20Feature%20branches.svg?cdnVersion=1286)
Every feature that is to be added to the project should have its own "feature" branch. Feature branches should have development branches as their parents and should not branch directly off of main branches.
<br> After a feature has been completed, it should be merged into the development branch. Direct commits to development branches should be made only in the case of small fixes/changes, most commits during development should be to the feature branches.
Commits should be realatively small and should not impact other features.

### Release branches , hotfix branches
The GitFlow models contains other branches named "release" branches and "hotfix" branches, for the purpouses of this project, we will not be using this part of the model.
<br>
## Branching and commit naming convention
This part of the document outlines the naming convention used for branching and the commits of this project.
<br> Branch names should not be capitalized.
### main
The branch used for official release versions of the app, no direct commits to this branch are allowed.
<br> Commits to this branch should have a version number attached to it.
<br> Versioning will generally follow Semantic Versioning 2.0.0. (MAJOR.MINOR.PATCH)
### development
The branch used for developing the app, direct commits to this branch should only be small fixed and changes.
<br> Merges to this branch should contain the ID of the associated jira task. 
### feature
The branch used for developing features of the application. Commits to this branch should containt short descriptions of the changes made. 
## Pull request and review conventions
Pull requests should always be reviewed by at least 2 members of the team before accepting.
<br> The pull review should not be merged as long as there are problems 
that have been pointed out by a team member and are not resolved.






