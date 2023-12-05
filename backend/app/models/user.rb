class User < ApplicationRecord

    has_many :owned_events, class_name: 'Event', foreign_key: 'owner_id', dependent: :destroy
    has_and_belongs_to_many :events, join_table: 'event_attendances', foreign_key: 'user_id'
    
    has_secure_password

    PASSWORD_FORMAT = /\A
        (?=.*\d)           # Must contain a digit
        (?=.*[[:^alnum:]]) # Must contain a symbol
        /x
    
    validates :email, presence: true
    validates :email, uniqueness: true, if: :email_changed?
    validates_format_of :email, :with => /\A(|(([A-Za-z0-9]+_+)|([A-Za-z0-9]+\-+)|([A-Za-z0-9]+\.+)|([A-Za-z0-9]+\++))*[A-Za-z0-9]+@((\w+\-+)|(\w+\.))*\w{1,63}\.[a-zA-Z]{2,6})\z/i
    normalizes :email, with: -> email { email.downcase.strip }
    validates :username, presence: true
    validates :username, uniqueness: true, if: :username_changed?
    validates :username, length: { maximum: 15 }

    validates :location, length: { maximum: 255 }
    
    validates :password, length: { minimum: 10, maximum: 20 }, if: :password_digest_changed?
    validates :password, format: { with: PASSWORD_FORMAT }, if: :password_digest_changed?
end
