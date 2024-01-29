class Api::V1::FriendshipsController < ApplicationController
    #before_action :authenticate_user, except: [:index, :show]
    before_action :set_friendship, only: %i[ show update destroy cancel ]
    
    # GET /friendships
    def index
        @friendships = Friendship.all
    
        render json: @friendships
    end
    
    # GET /friendships/1
    def show
        render json: @friendship
    end
    
    # POST /friendships
    def create
        @friendship = Friendship.new(friendship_params)
        
        if @friendship.save
            render json: @friendship, status: :created
        else
            render json: @friendship.errors, status: :unprocessable_entity
        end
    end
    
    # PATCH/PUT /friendships/1
    def update
        if @friendship.update(friendship_params)
            render json: @friendship
        else
            render json: @friendship.errors, status: :unprocessable_entity
        end
    end
    # DELETE /friendships/1
    def destroy
      puts "destroying friendship------ ", @friendship
        @friendship.destroy
    end

    # POST /friendships/1/cancel
    def cancel
      destroy
    end
    
    
    private
        # Use callbacks to share common setup or constraints between actions.
        def set_friendship
            @friendship = Friendship.find(params[:id])
        end
    
        # Only allow a list of trusted parameters through.
        def friendship_params
            params.require(:friendship).permit(:user_id, :friend_id)
        end
end