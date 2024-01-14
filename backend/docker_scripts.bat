if "%1"=="build" (
    docker build -t groupup .
) else if "%1"=="run" (
    docker run --rm -it -p 3000:3000 --env RAILS_MASTER_KEY=75c5bd8e46592fc90f6a7f8dba8cbbe8 groupup
) else (
    echo Invalid argument. Please use "build" or "run".
)