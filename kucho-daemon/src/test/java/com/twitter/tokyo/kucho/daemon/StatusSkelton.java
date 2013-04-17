/*
 * Copyright 2012 Twitter, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.twitter.tokyo.kucho.daemon;

import twitter4j.*;

import java.net.URL;
import java.util.Date;

class StatusSkelton implements Status {
    private final String FROM;
    private final String TEXT;
    StatusSkelton(String from, String text){
        FROM = from;
        TEXT = text;
    }

    @Override
    public Date getCreatedAt() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public long getId() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getText() {
        return TEXT;
    }

    @Override
    public String getSource() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isTruncated() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public long getInReplyToStatusId() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public long getInReplyToUserId() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getInReplyToScreenName() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public GeoLocation getGeoLocation() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Place getPlace() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isFavorited() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public User getUser() {
        return new User(){
            @Override
            public long getId() {
                return 0;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String getName() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String getScreenName() {
                return FROM;
            }

            @Override
            public String getLocation() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String getDescription() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public boolean isContributorsEnabled() {
                return false;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String getProfileImageURL() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String getBiggerProfileImageURL() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String getMiniProfileImageURL() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String getOriginalProfileImageURL() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public URL getProfileImageUrlHttps() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String getProfileImageURLHttps() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String getBiggerProfileImageURLHttps() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String getMiniProfileImageURLHttps() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String getOriginalProfileImageURLHttps() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String getURL() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public boolean isProtected() {
                return false;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public int getFollowersCount() {
                return 0;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public Status getStatus() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String getProfileBackgroundColor() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String getProfileTextColor() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String getProfileLinkColor() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String getProfileSidebarFillColor() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String getProfileSidebarBorderColor() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public boolean isProfileUseBackgroundImage() {
                return false;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public boolean isShowAllInlineMedia() {
                return false;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public int getFriendsCount() {
                return 0;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public Date getCreatedAt() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public int getFavouritesCount() {
                return 0;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public int getUtcOffset() {
                return 0;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String getTimeZone() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String getProfileBackgroundImageUrl() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String getProfileBackgroundImageURL() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String getProfileBackgroundImageUrlHttps() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String getProfileBannerURL() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String getProfileBannerRetinaURL() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String getProfileBannerIPadURL() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String getProfileBannerIPadRetinaURL() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String getProfileBannerMobileURL() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String getProfileBannerMobileRetinaURL() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public boolean isProfileBackgroundTiled() {
                return false;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String getLang() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public int getStatusesCount() {
                return 0;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public boolean isGeoEnabled() {
                return false;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public boolean isVerified() {
                return false;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public boolean isTranslator() {
                return false;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public int getListedCount() {
                return 0;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public boolean isFollowRequestSent() {
                return false;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public URLEntity[] getDescriptionURLEntities() {
                return new URLEntity[0];  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public URLEntity getURLEntity() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public int compareTo(User user) {
                return 0;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public RateLimitStatus getRateLimitStatus() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public int getAccessLevel() {
                return 0;  //To change body of implemented methods use File | Settings | File Templates.
            }
        };
    }

    @Override
    public boolean isRetweet() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Status getRetweetedStatus() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public long[] getContributors() {
        return new long[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public long getRetweetCount() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isRetweetedByMe() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public long getCurrentUserRetweetId() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isPossiblySensitive() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }


    @Override
    public int compareTo(Status status) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public UserMentionEntity[] getUserMentionEntities() {
        return new UserMentionEntity[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public URLEntity[] getURLEntities() {
        return new URLEntity[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public HashtagEntity[] getHashtagEntities() {
        return new HashtagEntity[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public MediaEntity[] getMediaEntities() {
        return new MediaEntity[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public RateLimitStatus getRateLimitStatus() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int getAccessLevel() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
