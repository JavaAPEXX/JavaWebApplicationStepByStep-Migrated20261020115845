import React from 'react';

type CommonFooterComponentProps = {
    data?: Record<string, any>;
};

export const CommonFooterComponent: React.FC<CommonFooterComponentProps> = ({ data = {} }) => {

    return (
        <div className="commonfootercomponent-wrapper">
            <footer className="footer">
        	<div>footer content</div>
        </footer>
        
        <script src="webjars/jquery/1.9.1/jquery.min.js"></script>
        <script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
        </div>
    );
};

export default CommonFooterComponent;
