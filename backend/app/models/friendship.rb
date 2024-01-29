class Friendship < ApplicationRecord
  
  belongs_to :user, class_name: 'User', foreign_key: 'user_id'
  belongs_to :friend, class_name: 'User', foreign_key: 'friend_id'

  # set user and friend as unique pair and write a custom error message
  validates :user, uniqueness: { scope: :friend_id, message: "You are already friends with this user." }

  # set user and friend as not the same
  validate :not_self

  def not_self
    errors.add(:friend_id, "can't be equal to user_id") if user_id == friend_id
  end


  validates :user_id, presence: true
  validates :friend_id, presence: true
  validates :status, inclusion: { in: ['pending', 'accepted', 'rejected'] }

end
