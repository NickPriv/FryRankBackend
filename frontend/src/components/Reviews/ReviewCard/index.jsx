import { PropTypes } from 'prop-types';
import { Card, CardBody, CardSubtitle, CardText, CardTitle } from 'reactstrap';

const propTypes = {
    title: PropTypes.string.isRequired,
    authorId: PropTypes.string.isRequired,
    score: PropTypes.string.isRequired,
    body: PropTypes.string.isRequired,
};

const ReviewCard = ({ title, authorId, score, body }) => {
    return (
        <Card
            style={{
                width: '36rem'
            }}
        >
            <CardBody>
                <CardTitle tag="h4">
                    {title}
                </CardTitle>
                <CardSubtitle
                    className="mb-2 text-muted"
                    tag="h6"
                >
                    Author: {authorId}
                </CardSubtitle>
                <CardSubtitle
                    className="mb-2 text-primary"
                    tag="h5"
                >
                    Score: {score}
                </CardSubtitle>
                <CardText>
                    {body}
                </CardText>
            </CardBody>
        </Card>
    )
}

ReviewCard.propTypes = propTypes;

export default ReviewCard;