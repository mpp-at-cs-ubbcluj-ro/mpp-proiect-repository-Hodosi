using System;

namespace client
{
    public enum CompetitionUserEvent
    {
        SaveParticipant
    };
        
    public class CompetitionUserEventArgs : EventArgs
    {
        private readonly CompetitionUserEvent userEvent;
        private readonly Object data;

        public CompetitionUserEventArgs(CompetitionUserEvent userEvent, object data)
        {
            this.userEvent = userEvent;
            this.data = data;
        }

        public CompetitionUserEvent UserEventType
        {
            get { return userEvent; }
        }

        public object Data
        {
            get { return data; }
        }
    }
}