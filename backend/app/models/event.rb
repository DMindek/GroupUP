class Event < ApplicationRecord
    belongs_to :owner, class_name: 'User'
    has_and_belongs_to_many :participants, class_name: 'User', join_table: 'event_attendances', foreign_key: 'event_id'


    validates :name, presence: true, length: {maximum: 25 }
    validates :description, presence: true, length: {maximum: 256 }
    validates :date, presence: true
    validates :date, presence: true, comparison: { greater_than: Date.current }
    validates :location, presence: true

    def as_json(options = {})
        super(options.merge(
            except: [:created_at, :updated_at],
            include: {
            owner: {
                except: [:created_at, :updated_at, :password_digest]
            },
            participants: {

            }
            }
        ))
    end

end
