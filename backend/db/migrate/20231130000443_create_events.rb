class CreateEvents < ActiveRecord::Migration[7.1]
  def change
    create_table :events do |t|
      t.text :name
      t.text :description
      t.datetime :date
      t.integer :duration
      t.integer :max_participants
      t.text :location

      t.timestamps
    end
  end
end
