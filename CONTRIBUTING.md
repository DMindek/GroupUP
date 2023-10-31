# Contributing to the project
To contribute to this project, you must follow the guidelines in this document.
## Disclaimer
All images in this document are the property of their respective owners and have been taken from the following link:
<br> https://www.atlassian.com/git/tutorials/comparing-workflows/gitflow-workflow <br>
For detailed descriptions of the model, you can visit the above link.

## Branching model
The branching model of this project follows the GitFlow model. The central premise is that there are separate branches for the release versions of the application and the versions that are in development. 
Following this are some main points that need to be adhered to.<br>
### Development and main branches
![Gitflow model 1](https://wac-cdn.atlassian.com/dam/jcr:a13c18d6-94f3-4fc4-84fb-2b8f1b2fd339/01%20How%20it%20works.svg?cdnVersion=1286)

<br>The "main" branch should be used for committing release versions of the software that must be fully tested. 
The "development" branch is used for managing the integration of functionalities into the main branch. 
Direct commits to the main are not allowed. <br>
<b>The code in this branch should always be compilable, and the code being merged should always be done via pull request.</b>

### Feature branches
![Gitflow model 2](https://wac-cdn.atlassian.com/dam/jcr:34c86360-8dea-4be4-92f7-6597d4d5bfae/02%20Feature%20branches.svg?cdnVersion=1286)
Every feature to be added to the project should have its own "feature" branch. Feature branches should have development branches as their parents and not units directly off main components.
<br> After a feature has been completed, it should be merged into the development branch. Direct commits to development branches should be made only for minor fixes/changes; most commits during development should be to the feature branches.
Commits should be relatively small and should not impact other features.

### Release branches, hotfix branches
The GitFlow models contain other branches named "release" branches and "hotfix" branches; for this project, we will not be using this part of the model.
<br>
## Branching and commit naming convention
This part of the document outlines the naming convention used for branching and the commits of this project.
<br> Branch names should not be capitalized.
### main
The branch used for official release versions of the app; no direct commits to this branch are allowed.
<br> Commits to this branch should have a version number.
<br> Versioning will generally follow Semantic Versioning 2.0.0. (MAJOR.MINOR.PATCH)
### development
The branch used for developing the app and direct commits to this branch should only be minor fixes and changes.
<br> Merges to this branch should contain the ID of the associated Jira task. 
### feature
The branch is used for developing features of the application.
<br> Commits to this branch should contain short descriptions of the changes made. 
### Commit format
All commit messages must be in the following format:
<br><br>
(context) (type) : (description)
<br>[optional body]
<br><br>
The context is the context of the commit. 
<br>
It can be one of the following or empty if the commit is not related to a specific context:
<br>
- be for commits related to the Back-End project (eg. MySQL)
- be for commits related to the Front-End project (eg. Kotlin)
<br><br>

The type is the type of the commit. It can be one of the following:  
- feat for new features
- fix for bug fixes
- refactor for refactoring
- docs for documentation
- test for tests
- chore for chores
<br><br>

The description is a short description of the commit.
<br><br>
The body is an optional body of the commit. It can be used to provide more details about the commit.
<br>
The commit message must be in English.
## Pull requests and review conventions
At least two team members should constantly review pull requests before accepting.
<br> The pull review should not be merged as long as a team member has pointed out problems that still need to be resolved.

# Contributing to the frontend code
In order to build and run the frontend code of this project you need to install Android Studio Giraffe 2022.3.1 or newer. When opening the project simply choose the "Open" option in the Android Studio starting screen and open the frontend folder.
<br> Android Studio should come with a virtual device that is already set up and capable of running the application (minimum SKD is 27).





