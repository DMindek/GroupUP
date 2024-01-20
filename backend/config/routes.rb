Rails.application.routes.draw do
  namespace :api do
    namespace :v1 do

      # Users routes
      resources :users
      post "/login", to: "users#login"
      post "/register", to: "users#create"
      post "/users/:id/edit", to: "users#update"
      get "/users/:id/owned_events", to: "users#owned_events"
      get "/users/:id/joined_events", to: "users#joined_events"

      # Events routes
      get "/events/available_events", to: "events#available_events"
      resources :events
      post "/events/:id/edit", to: "events#update"
      post "/events/:id/join", to: "events#join"
      post "/events/:id/leave", to: "events#leave"
    end
  end
  # Define your application routes per the DSL in https://guides.rubyonrails.org/routing.html

  # Reveal health status on /up that returns 200 if the app boots with no exceptions, otherwise 500.
  # Can be used by load balancers and uptime monitors to verify that the app is live.
  get "up" => "rails/health#show", as: :rails_health_check

  # Defines the root path route ("/")
  # root "posts#index"
end
