class Api::V1::UsersController < ApplicationController
  before_action :authenticate_user, except: [:create, :login, :index, :search]
  before_action :set_user, only: %i[ show update destroy owned_events joined_events add_friend friend_requests friends]

  # GET /users
  def index
    @users = User.all

    render json: @users
  end

  # GET /users/1
  def show
    render json: show_user(@user)
  end

  # GET /users/1/owned_events
  def owned_events
    render json: @user.owned_events
  end

  # GET /users/1/joined_events
  def joined_events
    render json: @user.events.where("date > ?", DateTime.now)
  end

  # POST /users
  def create
    @user = User.new(user_params)

    if @user.save
      render json: {message: "Successfully created a new user."}, status: :created
    else
      render json: @user.errors, status: :unprocessable_entity
    end
  end

  # PATCH/PUT /users/1
  def update
    if @user.update(user_update_params)
      render json: show_user(@user)
    else
      render json: @user.errors, status: :unprocessable_entity
    end
  end

  # DELETE /users/1
  def destroy
    @user.destroy!
  end

  # POST /login
  def login
    @user = User.find_by(email: params[:email])
    if not @user
      render json: { error: 'Incorrect email or password' }, status: :unprocessable_entity
    elsif @user&.authenticate(params[:password])
      render json: {
        token: JWT.encode({ user_id: @user.id }, Rails.application.credentials.secret_key_base[0]),
        email: @user.email,
        id: @user.id,
        username: @user.username,
        location: @user.location
      }
    else
      render json: { error: 'Incorrect email or password' }, status: :unprocessable_entity
    end
  end

  # GET /users/search/:username
  def search
    @users = User.where("username LIKE ?", "%#{params[:username]}%")
    render json: @users
  end

  # POST /users/:id/add_friend
  def add_friend
    @friend = User.find(params[:friend_id])
    @friendship = Friendship.new(user: @user, friend: @friend, status: 'pending')

    if @friendship.save
      render json: {message: "Successfully sent a friend request."}, status: :created
    else
      render json: @friendship.errors, status: :unprocessable_entity
    end
  end

  # GET /users/:id/friend_requests
  def friend_requests
    @friendships = Friendship.where(user: @user, status: 'pending').or(Friendship.where(friend: @user, status: 'accepted'))
    render json: @friendships, except: [:created_at, :updated_at]
  end

  # GET /users/:id/friends
  def friends
    @friendships = Friendship.where(user: @user, status: 'accepted' )
    render json: @friendships, except: [:created_at, :updated_at]
  end


  private
    # Use callbacks to share common setup or constraints between actions.
    def set_user
      @user = User.find(params[:id])
    end

    def show_user(user)
      return user.to_json()
    end



    # Only allow a list of trusted parameters through.
    def user_params
      params.require(:user).permit(:username, :location, :email, :password)
    end

    def user_update_params
      params.require(:user).permit(:username, :location, :email)
    end

    def authenticate_user
      auth_header = request.headers['Authorization']
      @user = nil
  
      if auth_header
        token = auth_header.split(' ').last
        begin
          payload = JWT.decode(token, Rails.application.credentials.secret_key_base[0])[0]
          @user = User.find_by(id: payload['user_id'])
        rescue JWT::DecodeError
          @user = nil
        end
      end
  
      unless @user
        render json: { error: 'unauthorized' }, status: :unauthorized
      end
    end
      
end
