# This file should ensure the existence of records required to run the application in every environment (production,
# development, test). The code here should be idempotent so that it can be executed at any point in every environment.
# The data can then be loaded with the bin/rails db:seed command (or created alongside the database with db:setup).
#
# Example:
#
#   ["Action", "Comedy", "Drama", "Horror"].each do |genre_name|
#     MovieGenre.find_or_create_by!(name: genre_name)
#   end

User.create!([
    {email: "admin@admin.com", username: "admin", password: "adminadmin10!", location: "Varazdin"}
])

Event.create!([
    {name: "Event1", description: "Event1", date: "2025-01-01", duration: 10, max_participants: 10, location: "Varazdin", owner_id: 1},
    {name: "Event2", description: "Event2", date: "2025-01-01", duration: 30, max_participants: 20, location: "Porto", owner_id: 1},
    {name: "Event3", description: "Event3", date: "2025-01-01", duration: 30, max_participants: 30, location: "Varazdin", owner_id: 1},
    {name: "Event4", description: "Event4", date: "2025-01-04", duration: 15, max_participants: 40, location: "Varazdin", owner_id: 1},
    {name: "Event5", description: "Event5", date: "2025-01-05", duration: 60, max_participants: 50, location: "Porto", owner_id: 1},
    {name: "Event6", description: "Event6", date: "2025-01-01", duration: 90, max_participants: 60, location: "Varazdin", owner_id: 1},
])