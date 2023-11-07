class Api::V1::UsersController < ApplicationController
  before_action :set_user, only: %i[ show update destroy ]
  before_action :authenticate_user, except: [:create, :login, :index]

  # GET /users
  def index
    @users = User.all

    render json: @users
  end

  # GET /users/1
  def show
    render json: @user
  end

  # POST /users
  def create
    @user = User.new(user_params)

    if @user.save
      render json: @user, status: :created
    else
      render json: @user.errors, status: :unprocessable_entity
    end
  end

  # PATCH/PUT /users/1
  def update
    if @user.update(user_params)
      render json: @user
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
    if @user&.authenticate(params[:password])
      render json: {
        token: JWT.encode({ user_id: @user.id }, Rails.application.credentials.secret_key_base[0]),
        email: @user.email
      }
    else
      render json: { error: 'unauthorized' }, status: :unauthorized
    end
  end

  private
    # Use callbacks to share common setup or constraints between actions.
    def set_user
      @user = User.find(params[:id])
    end


    # Only allow a list of trusted parameters through.
    def user_params
      params.require(:user).permit(:username, :location, :email, :password)
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
