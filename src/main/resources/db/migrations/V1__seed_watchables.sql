DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'watchablestatus') THEN
        CREATE TYPE watchablestatus AS ENUM ('UNKNOWN', 'ACTIVE', 'INACTIVE');
    END IF;
END $$;

CREATE SEQUENCE IF NOT EXISTS watchables_id_seq;

ALTER TABLE watchables ALTER COLUMN id SET DEFAULT nextval('watchables_id_seq');

INSERT INTO watchables (name, url, check_interval, status, created_at, updated_at)
VALUES 
  ('Google', 'https://www.google.com', 6000, 'UNKNOWN'::watchablestatus, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('GitHub', 'https://github.com', 4000, 'UNKNOWN'::watchablestatus, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('Twitter', 'https://twitter.com', 3000, 'UNKNOWN'::watchablestatus, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('Facebook', 'https://www.facebook.com', 3000, 'UNKNOWN'::watchablestatus, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('LinkedIn', 'https://www.linkedin.com', 5000, 'UNKNOWN'::watchablestatus, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('Instagram', 'https://www.instagram.com', 6000, 'UNKNOWN'::watchablestatus, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('YouTube', 'https://www.youtube.com', 8000, 'UNKNOWN'::watchablestatus, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('Reddit', 'https://www.reddit.com', 2000, 'UNKNOWN'::watchablestatus, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('Pinterest', 'https://www.pinterest.com', 3000, 'UNKNOWN'::watchablestatus, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('Snapchat', 'https://www.snapchat.com', 1000, 'UNKNOWN'::watchablestatus, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('WhatsApp', 'https://www.whatsapp.com', 3000, 'UNKNOWN'::watchablestatus, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('TikTok', 'https://www.tiktok.com', 2000, 'UNKNOWN'::watchablestatus, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('Tumblr', 'https://www.tumblr.com', 2000, 'UNKNOWN'::watchablestatus, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('Flickr', 'https://www.flickr.com', 2000, 'UNKNOWN'::watchablestatus, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('Vimeo', 'https://www.vimeo.com', 1500, 'UNKNOWN'::watchablestatus, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('Medium', 'https://www.medium.com', 3000, 'UNKNOWN'::watchablestatus, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('Quora', 'https://www.quora.com', 5000, 'UNKNOWN'::watchablestatus, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('Vine', 'https://www.vine.com', 8900, 'UNKNOWN'::watchablestatus, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('Periscope', 'https://www.periscope.com', 1000, 'UNKNOWN'::watchablestatus, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('Twitch', 'https://www.twitch.com', 2000, 'UNKNOWN'::watchablestatus, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('Telegram', 'https://www.telegram.com', 2700, 'UNKNOWN'::watchablestatus, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('Skype', 'https://www.skype.com', 9000, 'UNKNOWN'::watchablestatus, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('Slack', 'https://www.slack.com', 4400, 'UNKNOWN'::watchablestatus, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('Zoom', 'https://www.zoom.com', 5900, 'UNKNOWN'::watchablestatus, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('Google Meet', 'https://www.googlemeet.com', 6300, 'UNKNOWN'::watchablestatus, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('Microsoft Teams', 'https://www.microsoftteams.com', 5100, 'UNKNOWN'::watchablestatus, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('Cisco Webex', 'https://www.ciscowebex.com', 6800, 'UNKNOWN'::watchablestatus, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('Zoho Meeting', 'https://www.zohomeeting.com', 4900, 'UNKNOWN'::watchablestatus, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('GoToMeeting', 'https://www.gotomeeting.com', 3000, 'UNKNOWN'::watchablestatus, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('BlueJeans', 'https://www.bluejeans.com', 2000, 'UNKNOWN'::watchablestatus, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('Join.me', 'https://www.joinme.com', 3000, 'UNKNOWN'::watchablestatus, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('UberConference', 'https://www.uberconference.com', 4000, 'UNKNOWN'::watchablestatus, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('FreeConferenceCall', 'https://www.freeconferencecall.com', 5000, 'UNKNOWN'::watchablestatus, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('RingCentral', 'https://www.ringcentral.com', 6000, 'UNKNOWN'::watchablestatus, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('Jitsi Meet', 'https://www.jitsimeet.com', 7000, 'UNKNOWN'::watchablestatus, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('Whereby', 'https://www.whereby.com', 8000, 'UNKNOWN'::watchablestatus, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('Lifesize', 'https://www.lifesize.com', 9000, 'UNKNOWN'::watchablestatus, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('StarLeaf', 'https://www.starleaf.com', 8000, 'UNKNOWN'::watchablestatus, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('BlueJeans New', 'https://www.bluejeansnew.com', 3000, 'UNKNOWN'::watchablestatus, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('Zoom New', 'https://www.zoomnew.com', 4500, 'UNKNOWN'::watchablestatus, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('Google Meet New', 'https://www.googlemeetnew.com', 8000, 'UNKNOWN'::watchablestatus, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('Microsoft Teams New', 'https://www.microsoftteamsnew.com', 6000, 'UNKNOWN'::watchablestatus, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('Cisco Webex New', 'https://www.ciscowebexnew.com', 3300, 'UNKNOWN'::watchablestatus, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('Zoho Meeting New', 'https://www.zohomeetingnew.com', 7000, 'UNKNOWN'::watchablestatus, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('GoToMeeting New', 'https://www.gotomeetingnew.com', 3000, 'UNKNOWN'::watchablestatus, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('BlueJeans Updated', 'https://www.bluejeansupdated.com', 4200, 'UNKNOWN'::watchablestatus, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('Join.me Updated', 'https://www.joinmeupdated.com', 8000, 'UNKNOWN'::watchablestatus, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('UberConference Updated', 'https://www.uberconferenceupdated.com', 1000, 'UNKNOWN'::watchablestatus, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('FreeConferenceCall Updated', 'https://www.freeconferencecallupdated.com', 2000, 'UNKNOWN'::watchablestatus, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('RingCentral Updated', 'https://www.ringcentralupdated.com', 2200, 'UNKNOWN'::watchablestatus, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
