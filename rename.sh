#!/bin/bash

git filter-branch --commit-filter '
        if [ "$GIT_COMMITTER_NAME" = "fwilhelm" ];
        then
                GIT_COMMITTER_NAME="Franz Wilhelmstötter";
                GIT_AUTHOR_NAME="Franz Wilhelmstötter";
                GIT_COMMITTER_EMAIL="franz.wilhelmstoetter@gmail.com";
                GIT_AUTHOR_EMAIL="franz.wilhelmstoetter@gmail.com";
                git commit-tree "$@";
        elif [ "$GIT_COMMITTER_NAME" = "cvsuser" ];
        then
                GIT_COMMITTER_NAME="Franz Wilhelmstötter";
                GIT_AUTHOR_NAME="Franz Wilhelmstötter";
                GIT_COMMITTER_EMAIL="franz.wilhelmstoetter@gmail.com";
                GIT_AUTHOR_EMAIL="franz.wilhelmstoetter@gmail.com";
                git commit-tree "$@";
        elif [ "$GIT_COMMITTER_NAME" = "wweiser" ];
        then
                GIT_COMMITTER_NAME="Werner Weiser";
                GIT_AUTHOR_NAME="Werner Weiser";
                GIT_COMMITTER_EMAIL="werner.weiser@gmail.com";
                GIT_AUTHOR_EMAIL="werner.weiser@gmail.com";
                git commit-tree "$@";
        else
                git commit-tree "$@";
        fi' HEAD
