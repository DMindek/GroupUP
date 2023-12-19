class AddUniqueIndexToEventAttendances < ActiveRecord::Migration[7.1]
  def change
    add_index :event_attendances, [:event_id, :user_id], unique: true
  end
end
