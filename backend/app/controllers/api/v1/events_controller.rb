class Api::V1::EventsController < ApplicationController
    #before_action :authenticate_user, except: [:index, :show]
    before_action :set_event, only: %i[ show update destroy join ]
    
    # GET /events
    def index
        @events = Event.all
    
        render json: @events
    end
    
    # GET /events/1
    def show
        render json: show_event(@event)
    end

    
    # POST /events
    def create
        @event = Event.new(event_params)
        
        if @event.save
            render json: {message: "Successfully created a new Event."}, status: :created
        else
            render json: @event.errors, status: :unprocessable_entity
        end
    end
    
    # POST /events/1/join
    def join
        user = User.find(params[:user_id])
        event_attendance = EventAttendance.new(user: user, event: @event)

        if event_attendance.save
            render json: {message: "Successfully joined the Event."}, status: :created
        else
            render json: event_attendance.errors, status: :unprocessable_entity
        end
    end

    # PATCH/PUT /events/1
    def update   
        if @event.update(event_params)
        render json: show_event(@event)
        else
        render json: @event.errors, status: :unprocessable_entity
        end
    end
    
    # DELETE /events/1
    def destroy
        @event.destroy
    end
    
    private
    
    def show_event(event)
        return event.to_json()
    end
    
    def set_event
        @event = Event.find(params[:id])
    end
    
    def event_params
        params.require(:event).permit(:name, :description, :date, :duration, :max_participants, :location, :owner_id )
    end
    end