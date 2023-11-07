class User < ApplicationRecord
    
    has_secure_password

    PASSWORD_FORMAT = /\A
        (?=.*\d)           # Must contain a digit
        (?=.*[[:^alnum:]]) # Must contain a symbol
        /x
    
    validates :email, presence: true
    validates :email, uniqueness: true
    validates_format_of :email, :with => /\A(|(([A-Za-z0-9]+_+)|([A-Za-z0-9]+\-+)|([A-Za-z0-9]+\.+)|([A-Za-z0-9]+\++))*[A-Za-z0-9]+@((\w+\-+)|(\w+\.))*\w{1,63}\.[a-zA-Z]{2,6})\z/i
    normalizes :email, with: -> email { email.downcase.strip }
    validates :username, presence: true
    validates :username, uniqueness: true
    validates :username, length: { maximum: 15 }
    validates :location, length: { maximum: 255 }
    validates :password, length: { minimum: 10, maximum: 20 }
    validates :password, format: { with: PASSWORD_FORMAT }
end
